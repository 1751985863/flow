package com.nanfeng.flow.builder;

import com.nanfeng.flow.FlowDefinition;

public class FlowDefinitionBuilder<Context,Result> extends AbstractFlowDefinitionBuilder<Context> {


    protected ConditionalStepBuilder conditionalStepBuilder;

    public FlowDefinitionBuilder(String name, ConditionalStepBuilder<Context,Result> conditionalStepBuilder) {
        super(name);
        this.conditionalStepBuilder = conditionalStepBuilder;
    }

    public FlowDefinitionBuilder(String name) {
        this(name,null);
    }

    public StepBuilder<Context,Result> step(String name) {
        return new StepBuilder<>(this,name);
    }

    public FlowDefinition<Context,Result> build() {
        assert conditionalStepBuilder == null;
        return new FlowDefinition<>(this.getName(),this.getSteps());
    }

    public ConditionalStepBuilder<Context,Result> close(){
        assert this.conditionalStepBuilder != null;
        conditionalStepBuilder.accept(this);
        return conditionalStepBuilder;
    }
}
