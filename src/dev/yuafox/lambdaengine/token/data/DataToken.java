package dev.yuafox.lambdaengine.token.data;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.Token;

public class DataToken<T> extends Token {

    T value;

    public DataToken(T value) {
        super(0);
        this.value = value;
    }

    public <U> U unmask(Class<U> type, Memory variables) {
        return (U) this;
    }
    public <U> U getAs(Class<U> type) {
        return (U) this;
    }
    public T getValue(){
        return value;
    }
}