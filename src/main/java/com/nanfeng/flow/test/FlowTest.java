package com.nanfeng.flow.test;

import com.nanfeng.flow.test.context.JobContext;
import com.nanfeng.flow.test.context.JobResult;
import org.junit.Test;

public class FlowTest {
    @Test
    public void test() {
        JobContext context = new JobContext();
        context.setInput("入参");
        JobResult result = new JobFlow().definition().execute(context).getResult();
        System.out.println(result.getStatus());

    }
}
