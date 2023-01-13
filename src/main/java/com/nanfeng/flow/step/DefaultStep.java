package com.nanfeng.flow.step;

import com.nanfeng.flow.FlowResultHolder;
import com.nanfeng.flow.exception.MyException;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class DefaultStep<Context> extends PredicatableStep<Context, Void> {

    private Consumer<Context> consumer;

    public DefaultStep(String name, String flowName, Consumer<Context> consumer) {
        this(name,flowName,consumer,null);
    }

    public DefaultStep(String name, String flowName,  Consumer<Context> consumer,Predicate<Context> predicate) {
        super(name, flowName, predicate);
        this.consumer = consumer;
    }

    @Override
    public FlowResultHolder<Void> execute(Context context) {
        try {
            consumer.accept(context);
            return FlowResultHolder.empty();
        } catch (MyException me) {
            throw me;
        }
    }
}
