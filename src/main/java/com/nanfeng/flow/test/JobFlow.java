package com.nanfeng.flow.test;

import com.nanfeng.flow.FlowDefinition;
import com.nanfeng.flow.FlowDsl;
import com.nanfeng.flow.test.context.JobContext;
import com.nanfeng.flow.test.context.JobResult;

public class JobFlow implements FlowDsl<JobContext, JobResult> {
    @Override
    public FlowDefinition<JobContext, JobResult> definition() {
        return start("模拟使用flow开始")
                .step("步骤1，没有返回参数").invoke(this::noResult)
                .step("步骤2，有返回参数").invoke(this::hasResult)
                .step("步骤3，带执行条件的，能够执行").predicate(context ->{return context.getHasResult().equals("ture");}).invoke(this::fly)
                .step("步骤4，带执行条件的，不能够执行").predicate(context ->{return context.getHasResult().equals("false");}).invoke(this::closeFly)
                .step("步骤5，带执行条件的，获取JobContext的入参").predicate(JobContext::getStatus).invoke(this::newFly)
                .step("步骤6，结束，获取返回参数").end(this::buildResp).build();
/*        return start("模拟使用flow开始")
                .step("步骤1，没有返回参数").invoke(this::noResult)
                .step("步骤2，有返回参数").invoke(this::hasResult)
                .step("步骤6，结束，获取返回参数").end(this::buildResp).build();*/


    }

    private JobResult buildResp(JobContext context) {
        JobResult result = new JobResult();
        result.setStatus("S");
        return result;
    }

    private void newFly(JobContext context) {
        System.out.println("透过jobContext的属性进行条件判断");
    }

    private void closeFly(JobContext context) {
        System.out.println("关闭飞行模式");
    }

    private void fly(JobContext context) {
        System.out.println("开启飞行模式");
    }

    private void hasResult(JobContext jobContext) {
        System.out.println("步骤2，正在处理");
        jobContext.setHasResult("ture");
        jobContext.setStatus(false);
        System.out.println("步骤2，处理结束");


    }

    private void noResult(JobContext jobContext) {
        System.out.println("步骤1，正在处理");
        System.out.println("步骤1，处理结束");
    }
}
