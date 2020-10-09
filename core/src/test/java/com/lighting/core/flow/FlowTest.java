package com.lighting.core.flow;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.stream.IntStream;

@Slf4j
public class FlowTest {


    @Test
    public void arrayTest() {
        Demo[] demos = IntStream.range(0, 5).mapToObj(i -> new Demo(i + "", i + "name")).toArray(Demo[]::new);
        Flow.<Demo>fromArray(demos)
                .subscribe(demo -> System.out.println(demo))
                .onCompleted(d -> System.out.println("complete"))
                .onError((d, err) -> log.error("data has error", d))
                .start();

    }

    @Data
    @AllArgsConstructor
    private static class Demo {
        private String id;
        private String name;
    }
}