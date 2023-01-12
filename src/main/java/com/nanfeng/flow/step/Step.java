package com.nanfeng.flow.step;

import com.nanfeng.flow.FlowResultHolder;

public interface Step<Context,Result> {

    boolean hasAction(Context context);

    FlowResultHolder<Result> execute(Context context);

    String getName();

    String getFlowName();

}
