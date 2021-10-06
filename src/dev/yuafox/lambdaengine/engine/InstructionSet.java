package dev.yuafox.lambdaengine.engine;

import dev.yuafox.lambdaengine.token.data.DataToken;
import dev.yuafox.lambdaengine.token.data.ListData;

import java.util.List;
import java.util.Stack;

public class InstructionSet {
    private final List<Instruction> instructions;

    public InstructionSet(List<Instruction> instructions){
        this.instructions = instructions;
    }

    public Stack<Stack<DataToken<?>>> run(Memory variables, List<DataToken<?>> args, boolean changeScope) {
        Stack<Stack<DataToken<?>>> s = new Stack<>();
        Memory v;
        if(changeScope){
            v = new Memory(variables);
            v.put("args", new ListData(args));
        }else{
            v = variables;
        }
        for(Instruction i : instructions){
            s.add(i.execute(v));
        }
        return s;
    }

    @Override
    public String toString() {
        return "InstructionSet{" +
                "instructions=" + instructions +
                '}';
    }
}