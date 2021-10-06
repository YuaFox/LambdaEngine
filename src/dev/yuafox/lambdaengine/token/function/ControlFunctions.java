package dev.yuafox.lambdaengine.token.function;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.data.DataToken;
import dev.yuafox.lambdaengine.token.data.NumberData;
import dev.yuafox.lambdaengine.token.data.function.FunctionData;

import java.util.List;

public class ControlFunctions {
    @FunctionHandler(symbol = "if", type = FunctionType.GLOBAL)
    public DataToken<?> conditional(Memory variables, List<DataToken<?>> args) {
        FunctionData<?> conditional = args.get(0).unmask(FunctionData.class, variables);
        if(conditional.calculate(variables, args, false).unmask(NumberData.class, variables).getValue() > 0)
            return args.get(1).unmask(FunctionData.class, variables).calculate(variables, args, false);
        else
            return args.get(2).unmask(FunctionData.class, variables).calculate(variables, args, false);
    }

    @FunctionHandler(symbol = "while", type = FunctionType.GLOBAL)
    public DataToken<?> whileloop(Memory variables, List<DataToken<?>> args) {
        FunctionData<?> conditional = args.get(0).unmask(FunctionData.class, variables);
        while(conditional.calculate(variables, args, false).unmask(NumberData.class, variables).getValue() > 0){
            args.get(1).unmask(FunctionData.class, variables).calculate(variables, args, false);
        }
        return null;
    }
}