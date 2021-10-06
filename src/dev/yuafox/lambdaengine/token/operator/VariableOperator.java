package dev.yuafox.lambdaengine.token.operator;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.data.DataToken;
import dev.yuafox.lambdaengine.token.data.function.FunctionData;
import dev.yuafox.lambdaengine.token.data.function.MethodData;

import java.util.List;

public class VariableOperator extends OperatorToken {

    String[] references;

    public VariableOperator(String reference, int depth) {
        super(reference, null, null, 1000, -1, depth);
        this.references = reference.split("\\.");
    }

    public DataToken<?> calculate(Memory variables, List<DataToken<?>> args){
        DataToken<?> dataToken = variables.get(references[0]);
        if(references.length == 1) {
            return dataToken.unmask(FunctionData.class, variables).calculate(variables, args, true);
        }else if(references.length == 2){
            MethodData submethod = variables.getSubmethod(dataToken.getClass(), references[1]);
            return submethod.calculate(dataToken, variables, args);
        }else{
            return null;
        }
    }
}