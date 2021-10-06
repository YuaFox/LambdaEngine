package dev.yuafox.lambdaengine.engine;

import dev.yuafox.lambdaengine.token.Token;
import dev.yuafox.lambdaengine.token.data.DataToken;
import dev.yuafox.lambdaengine.token.operator.OperatorToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Instruction {

    private final List<Token> l;

    public Instruction(List<Token> l){
        this.l = l;
    }

    public Stack<DataToken<?>> execute(Memory variables){
        Stack<DataToken<?>> dataStack = new Stack<>();
        int commaCount = 1;
        for(Token t : this.l){
            if(t instanceof DataToken<?>){
                dataStack.push((DataToken<?>) t);
            }else if(t instanceof OperatorToken){
                OperatorToken operatorToken = (OperatorToken) t;
                if(operatorToken.getSymbol().equals(",")) {
                    commaCount++;
                }else {
                    List<DataToken<?>> arguments = new ArrayList<>();
                    int argumentAmount = operatorToken.getArguments();
                    if (argumentAmount == -1) {
                        argumentAmount = commaCount;
                        commaCount = 1;
                    }
                    for (int i = 0; i < argumentAmount; ++i) {
                        arguments.add(0, dataStack.pop());
                    }
                    dataStack.push(operatorToken.calculate(variables, arguments));
                }
            }
        }
        return dataStack;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "l=" + l +
                '}';
    }
}