package com.nanfeng.flow;

import com.nanfeng.flow.exception.MyException;
import com.nanfeng.flow.step.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;


public class FlowDefinition<Context,Result> implements Step<Context,Result> {

    private static ThreadLocal<LinkedList<Step>> stepCache = ThreadLocal.withInitial(LinkedList::new);

    private String name;

    private List<Step<Context,?>> steps;

    public FlowDefinition(String name, List<Step<Context, ?>> steps) {
        this.name = name;
        this.steps = steps;
    }

    @Override
    public boolean hasAction(Context context) {
        return true;
    }

    @Override
    public FlowResultHolder<Result> execute(Context context) {
        FlowResultHolder holder = FlowResultHolder.empty();
        Result result = null;
        for (Step<Context, ?> step : steps) {
            if (step.hasAction(context)) {
                try {
                    stepCache.get().addLast(step);
                    System.out.println("执行："+getCurrentFlowInfo());
                    holder = step.execute(context);
                    if (holder.isTerminate()) {
                        holder.setCurrentStepPath(getCurrentFlowInfo());
                        break;
                    }
                } catch (MyException me) {
                    System.out.println("[error] 执行："+getCurrentFlowInfo()+"业务报错");
                    me.printStackTrace();
                    throw me;
                } catch (Exception e) {
                    System.out.println("[error] 执行："+getCurrentFlowInfo()+"未知报错");
                    e.printStackTrace();
                    throw e;
                }finally {
                    stepCache.get().removeLast();
                }
            } else {
                stepCache.get().addLast(step);
                System.out.println("跳过："+ getCurrentFlowInfo());
                stepCache.get().removeLast();
            }
        }
        try {
            result = (Result) holder.getResult();
        } catch (Exception e) {
            System.out.println("流程$"+name+"最终获取返回报文报错");
        }
        System.out.println("流程$"+name+"结束：返回"+result);
        return holder;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFlowName() {
        return null;
    }

    private String getCurrentFlowInfo() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Step> stepInfo = stepCache.get();
        if (stepInfo.size() == 0) {
            return "";
        } else {
            return sb.append("$")
                    .append(stepInfo.getLast().getFlowName())
                    .append("#")
                    .append(stepInfo.getLast().getName())
                    .toString();
        }
    }

    private String getWholeFlowInfo() {
        StringBuilder sb = new StringBuilder();
        for(Step step : stepCache.get()) {
            sb.append("$").append(step.getFlowName()).append("#").append(step.getName());
            sb.append(" ");
        }
        return sb.toString();
    }
}
