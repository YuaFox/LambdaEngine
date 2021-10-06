package dev.yuafox.lambdaengine.token.data.function;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.data.DataToken;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodData extends FunctionData<Method> {

    private Object o;

    public MethodData(Object o, Method m) {
        super(m);
        this.o = o;
    }

    @Override
    public DataToken<?> calculate(Memory variables, List<DataToken<?>> args, boolean changeScope) {
        try {
            return (DataToken<?>) this.getValue().invoke(this.o, variables, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DataToken<?> calculate(Object o, Memory variables, List<DataToken<?>> args) {
        try {
            return (DataToken<?>) this.getValue().invoke(o, variables, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}