package com.nanfeng.flow.step;

import com.nanfeng.flow.FlowDefinition;
import com.nanfeng.flow.FlowResultHolder;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SubFlowStep<Context,Result,SubContext,SubResult> extends PredicatableStep<Context,Result> {

    private FlowDefinition<SubContext,SubResult> subFlowDefinition;

    private Function<Context,SubContext> contextMapper;

    private BiConsumer<SubResult,Context> resultConsumer;

    public SubFlowStep(String name, String flowName, Predicate<Context> predicate,
                       FlowDefinition<SubContext, SubResult> subFlowDefinition,
                       Function<Context, SubContext> contextFunction,
                       BiConsumer<SubResult, Context> resultConsumer) {
        super(name, flowName, predicate);
        this.subFlowDefinition = subFlowDefinition;
        this.contextMapper = contextFunction;
        this.resultConsumer = resultConsumer;
    }

    @Override
    public FlowResultHolder<Result> execute(Context context) {
        SubContext subContext = contextMapper.apply(context);
        FlowResultHolder<SubResult> subResult = subFlowDefinition.execute(subContext);
        resultConsumer.accept(subResult.getResult(),context);
        return FlowResultHolder.empty();
    }
}
