package com.nanfeng.flow;

import com.nanfeng.flow.step.Step;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FlowResultHolder<Result> {

    private Step currentStep;

    @Getter
    @Setter
    private String currentStepPath;

    @Getter
    private Result result;

    @Getter
    private boolean terminate;

    private FlowResultHolder(boolean terminate, Result result) {

        this.terminate = terminate;
        this.result = result;

    }

    public static FlowResultHolder empty() {
        return new FlowResultHolder(false,null);
    }

    public static <Result> FlowResultHolder<Result> of(Result result) {
        return new FlowResultHolder<>(true,result);
    }

}
