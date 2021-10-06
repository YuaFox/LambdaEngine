package dev.yuafox.lambdaengine.token.data.function;

import dev.yuafox.lambdaengine.engine.InstructionSet;
import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.data.DataToken;

import java.util.List;

public class CodeData extends FunctionData<InstructionSet> {

    public CodeData(InstructionSet value) {
        super(value);
    }

    @Override
    public DataToken<?> calculate(Memory variables, List<DataToken<?>> args, boolean changeScope) {
        return this.getValue().run(variables, args, changeScope).peek().peek();
    }
}