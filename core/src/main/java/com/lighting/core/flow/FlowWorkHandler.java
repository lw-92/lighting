package com.lighting.core.flow;

import com.lmax.disruptor.WorkHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class FlowWorkHandler<T> implements WorkHandler<FlowEvent<T>> {

    private String handlerName;

    @Override
    public void onEvent(FlowEvent<T> event) throws Exception {
        log.debug("work handler start:{}", handlerName);
        T data = event.getData();
        Optional.ofNullable(event.getSubscribe()).ifPresent(tConsumer -> {
            try {
                tConsumer.accept(data);
                Optional.ofNullable(event.getOnCompleted()).ifPresent(com -> com.accept(data));
            } catch (Exception e) {
                Optional.ofNullable(event.getOnError()).ifPresent(err -> {
                    err.accept(data, e);
                });
            }
        });
    }
}
