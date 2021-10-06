package dev.yuafox.lambdaengine.token.data;

import dev.yuafox.lambdaengine.engine.Memory;

public class VariableData extends DataToken<String> {

    public VariableData(String reference) {
        super(reference);
    }

    @Override
    public <U> U unmask(Class<U> type, Memory variables) {
        return (U) variables.get(this.value);
    }
}