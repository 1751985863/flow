package com.nanfeng.flow.step;

import com.nanfeng.flow.FlowResultHolder;
import com.nanfeng.flow.builder.ConditionalStepBuilder;

import java.util.LinkedList;
import java.util.Objects;

public class ConditionalStep<Context,Result> extends AbstractStep<Context,Result> {

    private String name;

    private LinkedList<ConditionalStepBuilder.Tuple<Context,Result>> conditionMap = new LinkedList<>();

    private Step<Context,Result> otherStep = null;

    public ConditionalStep(String name, String flowName, LinkedList<ConditionalStepBuilder.Tuple<Context, Result>> conditionMap, Step<Context, Result> otherStep) {
        super(name, flowName);
        Objects.requireNonNull(conditionMap);
        this.conditionMap = conditionMap;
        this.otherStep = otherStep;
    }

    @Override
    public boolean hasAction(Context context) {
        return false;
    }

    @Override
    public FlowResultHolder<Result> execute(Context context) {
        for (ConditionalStepBuilder.Tuple<Context,Result> tuple: conditionMap) {
            if (tuple.getPredicate().test(context)) {
                return tuple.getSubFlow().execute(context);
            }
        }
        if (otherStep != null) {
            return otherStep.execute(context);
        }
        return FlowResultHolder.empty();
    }
}
