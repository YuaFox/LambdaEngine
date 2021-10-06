package dev.yuafox.lambdaengine.token.function;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.data.DataToken;

import java.util.List;

public class DebugFunctions {
    @FunctionHandler(symbol = "print", type = FunctionType.GLOBAL)
    public DataToken<?> print(Memory variables, List<DataToken<?>> args) {
        System.out.println(args.get(0).unmask(DataToken.class, variables).getValue());
        return null;
    }
}