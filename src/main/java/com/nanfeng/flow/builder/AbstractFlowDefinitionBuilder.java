package com.nanfeng.flow.builder;

import com.nanfeng.flow.step.Step;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class AbstractFlowDefinitionBuilder<Context> {

    @Getter
    private List<Step<Context,?>> steps = new LinkedList<>();

    @Getter
    private String name;

    void addStep(Step<Context,?> step) {
        steps.add(step);
    }

    public AbstractFlowDefinitionBuilder(String name) {
        this.name = name;
    }
}
