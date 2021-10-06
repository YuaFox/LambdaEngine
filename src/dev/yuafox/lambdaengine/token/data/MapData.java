package dev.yuafox.lambdaengine.token.data;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.function.FunctionHandler;
import dev.yuafox.lambdaengine.token.function.FunctionType;

import java.util.HashMap;
import java.util.List;

public class MapData extends DataToken<HashMap<String, DataToken<?>>> {

    public MapData() {
        super(new HashMap<>());
    }

    @FunctionHandler(symbol = "map", type = FunctionType.GLOBAL)
    public DataToken<?> create(Memory variables, List<DataToken<?>> l) {
        return new MapData();
    }

    @FunctionHandler(symbol = "put", type = FunctionType.SUBFUNCTION)
    public DataToken<?> put(Memory variables, List<DataToken<?>> l) {
        String k = l.get(0).unmask(StringData.class, variables).getValue();
        DataToken<?> v = l.get(1).unmask(DataToken.class, variables);
        value.put(k, v);
        return this;
    }

    @FunctionHandler(symbol = "get", type = FunctionType.SUBFUNCTION)
    public DataToken<?> get(Memory variables, List<DataToken<?>> l) {
        String k = l.get(0).unmask(StringData.class, variables).getValue();
        return value.get(k);
    }
}