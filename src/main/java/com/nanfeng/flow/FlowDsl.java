package com.nanfeng.flow;

import com.nanfeng.flow.builder.FlowDefinitionBuilder;

public interface FlowDsl<Context,Result> {

    default FlowDefinitionBuilder<Context,Result> start(String name) {
        return new FlowDefinitionBuilder<>(name);
    }

    FlowDefinition<Context,Result> definition();
}
