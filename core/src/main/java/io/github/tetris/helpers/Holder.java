package io.github.tetris.helpers;

import java.util.concurrent.atomic.AtomicReference;

public class Holder<T> {
    private final AtomicReference<T> value;

    public T getValue(){
        return this.value.get();
    }
    public void setValue(T value){
        this.value.set(value);
    }

    public Holder(T value){
        this.value = new AtomicReference<>(value);
    }
}
