package dev.yuafox.lambdaengine.engine;

import dev.yuafox.lambdaengine.token.operator.OperatorHandler;
import dev.yuafox.lambdaengine.token.operator.OperatorToken;

import java.lang.reflect.Method;
import java.util.HashMap;

public class OperatorDictionary {
    private HashMap<String, Pair<Object, Method>> operators = new HashMap<>();

    public void registerOperator(Object o) {
        Class<?> clazz = o.getClass();
        for(Method m : clazz.getMethods()){
            OperatorHandler annotation = m.getAnnotation(OperatorHandler.class);
            if(annotation != null){
                operators.put(annotation.symbol(), new Pair<>(o, m));
            }
        }
    }

    public boolean containsOperator(String id){
        return operators.containsKey(id);
    }

    public OperatorToken getOperator(String id, Integer depth){
        Pair<Object, Method> operatorData = operators.get(id);
        OperatorHandler annotation = operatorData.getValue().getAnnotation(OperatorHandler.class);
        return new OperatorToken(annotation.symbol(), operatorData.getKey(), operatorData.getValue(), annotation.priority(), annotation.arguments(), depth);
    }
}