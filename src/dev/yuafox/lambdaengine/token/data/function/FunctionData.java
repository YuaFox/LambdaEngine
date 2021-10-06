package dev.yuafox.lambdaengine.token.data.function;

import dev.yuafox.lambdaengine.engine.*;
import dev.yuafox.lambdaengine.token.data.DataToken;

import java.util.List;

public abstract class FunctionData<T> extends DataToken<T> {

    public FunctionData(T value) {
        super(value);
    }

    public abstract DataToken<?> calculate(Memory variables, List<DataToken<?>> args, boolean changeScope);
}