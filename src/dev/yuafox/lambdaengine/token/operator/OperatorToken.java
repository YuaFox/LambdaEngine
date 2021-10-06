package dev.yuafox.lambdaengine.token.operator;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.Token;
import dev.yuafox.lambdaengine.token.data.DataToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class OperatorToken extends Token {

    private final String symbol;
    private final Object o;
    private final Method m;
    private final int arguments;
    private final int priority;

    public OperatorToken(String symbol, Object o, Method m, int priority, int arguments, int depth) {
        super(depth);
        this.symbol = symbol;
        this.o = o;
        this.m = m;
        this.arguments = arguments;
        this.priority = priority;
    }

    public String getSymbol() { return symbol; }
    public int getArguments(){
        return arguments;
    }
    public int getPriority(){
        return priority;
    }

    public DataToken<?> calculate(Memory variables, List<DataToken<?>> args){
        try {
            return (DataToken<?>) m.invoke(o, variables, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "OperatorToken{" +
                "symbol='" + symbol + '\'' +
                ", arguments=" + arguments +
                ", priority=" + priority +
                '}';
    }
}
