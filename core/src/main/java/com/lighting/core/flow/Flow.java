package com.lighting.core.flow;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Flow<T> {

    private Consumer<T> onCompleted;
    private BiConsumer<T, Throwable> onError;
    private Consumer<T> subscribe;

    // todo more dataType
    private T[] arrayData;
    private Stream<T> streamData;

    private Disruptor<T> publishOn;

    private Flow() {

    }

    public Flow<T> onCompleted(Consumer<T> onCompleted) {
        this.onCompleted = onCompleted;
        return this;
    }

    public Flow<T> publishOn(Disruptor<T> publishOn) {
        this.publishOn = publishOn;
        return this;
    }

    public Flow<T> subscribe(Consumer<T> subscribe) {
        this.subscribe = subscribe;
        return this;
    }

    public Flow<T> onError(BiConsumer<T, Throwable> onError) {
        this.onError = onError;
        return this;
    }


    public static <T> Flow<T> fromArray(T[] array) {
        Flow<T> arrayFlow = new Flow<>();
        arrayFlow.arrayData = array;
        return arrayFlow;
    }

    public static <T> Flow<T> fromStream(Stream<T> stream) {
        Flow<T> streamFlow = new Flow<>();
        streamFlow.streamData = stream;
        return streamFlow;
    }

    public void start() {

    }
}
