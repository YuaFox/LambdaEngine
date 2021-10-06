package dev.yuafox.lambdaengine.engine;

import dev.yuafox.lambdaengine.token.data.DataToken;
import dev.yuafox.lambdaengine.token.data.function.MethodData;
import dev.yuafox.lambdaengine.token.function.FunctionHandler;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Memory {


    private HashMap<String, DataToken<?>> memory;
    private HashMap<Class<?>, HashMap<String, MethodData>> functionDic;


    public Memory(){
        this.memory = new HashMap<>();
        this.functionDic = new HashMap<>();
    }

    public Memory(Memory memory){
        this();
        this.memory.putAll(memory.memory);
        this.functionDic = memory.functionDic;
    }

    public void put(String k, DataToken<?> v){
        this.memory.put(k, v);
    }

    public DataToken<?> get(String k){
        return this.memory.get(k);
    }

    public void registerSubmethod(Object o, Method method){
        HashMap<String, MethodData> objectFunctions = this.functionDic.computeIfAbsent(o.getClass(), k -> new HashMap<>());
        objectFunctions.put(method.getAnnotation(FunctionHandler.class).symbol(), new MethodData(o, method));
    }

    public MethodData getSubmethod(Class<?> clazz, String s){
        HashMap<String, MethodData> objectFunctions = this.functionDic.get(clazz);
        if(objectFunctions != null){
            return objectFunctions.get(s);
        }else{
            return null;
        }
    }
}