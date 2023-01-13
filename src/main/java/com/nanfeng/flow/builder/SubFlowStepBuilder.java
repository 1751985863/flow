package com.nanfeng.flow.builder;

import com.nanfeng.flow.FlowDefinition;
import com.nanfeng.flow.step.SubFlowStep;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SubFlowStepBuilder<Context,Result,SubContext,SubResult> {

    private String name;

    private FlowDefinitionBuilder<Context,Result> flow;

    private FlowDefinition<SubContext,SubResult> subFlowDefinition;

    private Function<Context,SubContext> contextMapper;

    private BiConsumer<SubResult,Context> resultConsumer;

    private Predicate<Context> predicate;

    public  SubFlowStepBuilder(String name, Predicate<Context> predicate, Function<Context,SubContext> contextMapper,
                               BiConsumer<SubResult, Context> resultConsumer, FlowDefinitionBuilder<Context,Result> flow) {
        this.name = name;
        this.predicate = predicate;
        this.contextMapper = contextMapper;
        this.resultConsumer = resultConsumer;
        this.flow = flow;
    }


    public SubFlowStepBuilder<Context,Result,SubContext,SubResult> flow(FlowDefinition<SubContext,SubResult> subFlowDefinition) {
        this.subFlowDefinition = subFlowDefinition;
        return this;
    }

    public FlowDefinitionBuilder<Context,Result> finish() {
        flow.addStep(new SubFlowStep<>(name, flow.getName(), predicate,
                subFlowDefinition,contextMapper,resultConsumer));
        return flow;
    }

}
