package dev.yuafox.lambdaengine.token.data;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.function.FunctionHandler;
import dev.yuafox.lambdaengine.token.function.FunctionType;

import java.util.ArrayList;
import java.util.List;

public class ListData extends DataToken<List<DataToken<?>>> {

    public ListData() {
        super(new ArrayList<>());
    }
    public ListData(List<DataToken<?>> l) {
        super(l);
    }

    @FunctionHandler(symbol = "list", type = FunctionType.GLOBAL)
    public DataToken<?> create(Memory variables, List<DataToken<?>> args) {
        return new ListData();
    }

    @FunctionHandler(symbol = "get", type = FunctionType.SUBFUNCTION)
    public DataToken<?> get(Memory variables, List<DataToken<?>> args) {
        Double index = args.get(0).unmask(NumberData.class, variables).getValue();
        return value.get(index.intValue());
    }

    @FunctionHandler(symbol = "put", type = FunctionType.SUBFUNCTION)
    public DataToken<?> put(Memory variables, List<DataToken<?>> args) {
        if(args.size() == 1){
            DataToken<?> v = args.get(0).unmask(DataToken.class, variables);
            value.add(v);
        }else{
            Double index = args.get(0).unmask(NumberData.class, variables).getValue();
            DataToken<?> v = args.get(1).unmask(DataToken.class, variables);
            value.add(index.intValue(), v);
        }
        return this;
    }

    @FunctionHandler(symbol = "size", type = FunctionType.SUBFUNCTION)
    public DataToken<?> size(Memory variables, List<DataToken<?>> args) {
        return new NumberData((double) value.size());
    }
}