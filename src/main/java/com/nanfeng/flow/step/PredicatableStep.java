package com.nanfeng.flow.step;


import com.nanfeng.flow.FlowResultHolder;

import java.util.function.Predicate;

public abstract class PredicatableStep<Context,Result> extends AbstractStep<Context,Result> {


    private Predicate<Context> predicate;


    public Predicate<Context> getPredicate() {
        return predicate;
    }

    @Override
    public boolean hasAction(Context context) {
        return getPredicate() == null || getPredicate().test(context);
    }

    public PredicatableStep(String name, String flowName, Predicate<Context> predicate) {
        super(name,flowName);
        this.predicate = predicate;
    }
}
