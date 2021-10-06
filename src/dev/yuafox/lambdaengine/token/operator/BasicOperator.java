package dev.yuafox.lambdaengine.token.operator;

import dev.yuafox.lambdaengine.engine.Memory;
import dev.yuafox.lambdaengine.token.data.DataToken;
import dev.yuafox.lambdaengine.token.data.NumberData;
import dev.yuafox.lambdaengine.token.data.VariableData;

import java.util.List;

public class BasicOperator {

    @OperatorHandler(symbol = "+", arguments = 2, priority = 10)
    public DataToken<?> plus(Memory variables, List<DataToken<?>> args) {
        Double n1 = args.get(0).unmask(NumberData.class, variables).getValue();
        Double n2 = args.get(1).unmask(NumberData.class, variables).getValue();
        return new NumberData(n1+n2);
    }

    @OperatorHandler(symbol = "-", arguments = 2, priority = 10)
    public DataToken<?> minus(Memory variables, List<DataToken<?>> args) {
        Double n1 = args.get(0).unmask(NumberData.class, variables).getValue();
        Double n2 = args.get(1).unmask(NumberData.class, variables).getValue();
        return new NumberData(n1-n2);
    }

    @OperatorHandler(symbol = "*", arguments = 2, priority = 20)
    public DataToken<?> product(Memory variables, List<DataToken<?>> args) {
        Double n1 = args.get(0).unmask(NumberData.class, variables).getValue();
        Double n2 = args.get(1).unmask(NumberData.class, variables).getValue();
        return new NumberData(n1*n2);
    }

    @OperatorHandler(symbol = "/", arguments = 2, priority = 20)
    public DataToken<?> quotient(Memory variables, List<DataToken<?>> args) {
        Double n1 = args.get(0).unmask(NumberData.class, variables).getValue();
        Double n2 = args.get(1).unmask(NumberData.class, variables).getValue();
        return new NumberData(n1/n2);
    }

    @OperatorHandler(symbol = "=", arguments = 2, priority = 0)
    public DataToken<?> assign(Memory variables, List<DataToken<?>> args) {
        String reference = args.get(0).getAs(VariableData.class).getValue();
        DataToken<?> data = args.get(1).unmask(DataToken.class, variables);
        variables.put(reference, data);
        return null;
    }

    @OperatorHandler(symbol = "==", arguments = 2, priority = 0)
    public DataToken<?> equal(Memory variables, List<DataToken<?>> args) {
        Object o1 = args.get(0).unmask(DataToken.class, variables).getValue();
        Object o2 = args.get(1).unmask(DataToken.class, variables).getValue();
        return new NumberData(o1.equals(o2) ? 1d : 0d);
    }

    @OperatorHandler(symbol = "!=", arguments = 2, priority = 0)
    public DataToken<?> notEqual(Memory variables, List<DataToken<?>> args) {
        Object o1 = args.get(0).unmask(DataToken.class, variables).getValue();
        Object o2 = args.get(1).unmask(DataToken.class, variables).getValue();
        return new NumberData(o1.equals(o2) ? 0d : 1d);
    }

    @OperatorHandler(symbol = ">", arguments = 2, priority = 0)
    public DataToken<?> bigger(Memory variables, List<DataToken<?>> args) {
        Double o1 = args.get(0).unmask(NumberData.class, variables).getValue();
        Double o2 = args.get(1).unmask(NumberData.class, variables).getValue();
        return new NumberData(o1 > o2  ? 1d : 0d);
    }

    @OperatorHandler(symbol = "<", arguments = 2, priority = 0)
    public DataToken<?> smaller(Memory variables, List<DataToken<?>> args) {
        Double o1 = args.get(0).unmask(NumberData.class, variables).getValue();
        Double o2 = args.get(1).unmask(NumberData.class, variables).getValue();
        return new NumberData(o1 < o2  ? 1d : 0d);
    }
}
