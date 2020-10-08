package com.lighting.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowEvent<T> {

    private T data;

    private Consumer<T> onCompleted;

    private BiConsumer<T, Throwable> onError;

    private Consumer<T> subscribe;

    private long sequence;
}
