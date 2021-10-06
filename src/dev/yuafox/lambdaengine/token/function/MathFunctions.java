package dev.yuafox.lambdaengine.token.function;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.data.DataToken;
import dev.yuafox.lambdaengine.token.data.NumberData;
import java.util.List;

public class MathFunctions {

    @FunctionHandler(symbol = "min", type = FunctionType.GLOBAL)
    public DataToken<?> min(Memory variables, List<DataToken<?>> l) {
        return new NumberData(0d);
    }
}