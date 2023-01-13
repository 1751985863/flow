package com.nanfeng.flow.builder;

import com.nanfeng.flow.FlowDefinition;
import com.nanfeng.flow.step.ConditionalStep;
import com.nanfeng.flow.step.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedList;
import java.util.function.Predicate;

public class ConditionalStepBuilder<Context,Result> {

    private final FlowDefinitionBuilder<Context,Result> flow;

    private String name;

    private LinkedList<Tuple<Context,Result>> conditionMap = new LinkedList<>();

    private Predicate<Context> currentCondition;

    private FlowDefinition<Context,Result> otherwiseStep = null;

    public ConditionalStepBuilder(String name,FlowDefinitionBuilder<Context,Result> flow) {
        this.flow = flow;
        this.name = name;
    }

    public FlowDefinitionBuilder<Context,Result> when(String name,Predicate<Context> predicate) {
        this.currentCondition = predicate;
        return new FlowDefinitionBuilder<>(name,this);
    }

    public FlowDefinitionBuilder<Context,Result> otherwise(String name) {
        this.currentCondition = null;
        return new FlowDefinitionBuilder<>(name,this);
    }

    public FlowDefinitionBuilder<Context,Result> finish() {
        flow.addStep(new ConditionalStep<>(name,flow.getName(),conditionMap,otherwiseStep));
        return flow;
    }

    public ConditionalStepBuilder accept(FlowDefinitionBuilder<Context,Result> subFlow) {
        if (this.currentCondition == null) {
            otherwiseStep = subFlow.build();
        } else {
            conditionMap.add(new Tuple<>(this.currentCondition,subFlow.build()));
        }
        return this;
    }
    @AllArgsConstructor
    public static final class Tuple<Context,Result> {
        @Getter
        Predicate<Context> predicate;
        @Getter
        Step<Context,Result> subFlow;
    }
}
