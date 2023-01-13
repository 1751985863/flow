package com.nanfeng.flow.step;

import com.nanfeng.flow.FlowResultHolder;

import java.util.function.Function;
import java.util.function.Predicate;

public class EndStep<Context,Result> extends PredicatableStep<Context,Result>{

    private Function<Context,Result> mapper;

    public EndStep(String name, String flowName, Predicate<Context> predicate, Function<Context, Result> mapper) {
        super(name, flowName, predicate);
        this.mapper = mapper;
    }

    @Override
    public FlowResultHolder<Result> execute(Context context) {
        Result result = mapper.apply(context);
        return FlowResultHolder.of(result);
    }

    @Override
    public String getName() {
        return super.getName() == null?"ending":super.getName();
    }
}
