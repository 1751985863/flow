package com.nanfeng.flow.step;

import lombok.Getter;

public abstract class AbstractStep<Context,Result> implements Step<Context,Result> {

    @Getter
    private String name;

    @Getter
    private String flowName;

    public AbstractStep(String name, String flowName) {
        this.name = name;
        this.flowName = flowName;
    }
}
