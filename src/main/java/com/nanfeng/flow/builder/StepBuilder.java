package com.nanfeng.flow.builder;

import com.nanfeng.flow.step.DefaultStep;
import com.nanfeng.flow.step.EndStep;


import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StepBuilder<Context,Result>{

    final FlowDefinitionBuilder<Context,Result> flow;

    String step;

    Predicate<Context> predicate = null;

    public StepBuilder(FlowDefinitionBuilder<Context, Result> flow, String step) {
        this.flow = flow;
        this.step = step;
    }

    public FlowDefinitionBuilder<Context,Result> invoke(Consumer<Context> consumer) {
        flow.addStep(new DefaultStep<>(step,flow.getName(),consumer,predicate));
        return flow;
    }

    public FlowDefinitionBuilder<Context,Result> end(Function<Context,Result> mapper) {
        flow.addStep(new EndStep<>(step,flow.getName(),mapper,predicate));
        return flow;
    }

    public ConditionalStepBuilder<Context,Result> conditional() {
        return new ConditionalStepBuilder<>(step,flow);
    }

    public <SubContext,SubResult> SubFlowStepBuilder<Context,Result,SubContext,SubResult>
    subflow(Function<Context,SubContext> contextMapper, BiConsumer<SubResult,Context> resultConsumer) {
        return new SubFlowStepBuilder<>(step,predicate,contextMapper,resultConsumer,flow);
    }

    public StepBuilder<Context,Result> predicate(Predicate<Context> predicate) {
        this.predicate = predicate;
        return this;
    }
}
