package dev.yuafox.lambdaengine.engine;

import dev.yuafox.lambdaengine.token.Token;
import dev.yuafox.lambdaengine.token.data.*;
import dev.yuafox.lambdaengine.token.data.function.CodeData;
import dev.yuafox.lambdaengine.token.data.function.MethodData;
import dev.yuafox.lambdaengine.token.function.*;
import dev.yuafox.lambdaengine.token.operator.BasicOperator;
import dev.yuafox.lambdaengine.token.operator.OperatorToken;
import dev.yuafox.lambdaengine.token.operator.VariableOperator;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LambdaEngine {

    final private static Pattern pattern = Pattern.compile("(^[0-9.]+|^[A-Za-z_.]+)|(^[^A-Za-z0-9(){}\\[\\]\",]+)|(^[(),])|(^\".*?(?<![\\\\])\")");
    private static Memory memory = new Memory();

    public static void registerFunctions(Object o){
        Class<?> clazz = o.getClass();
        for(Method m : clazz.getMethods()){
            FunctionHandler annotation = m.getAnnotation(FunctionHandler.class);
            if(annotation != null){
                if(annotation.type() == FunctionType.GLOBAL)
                    memory.put(annotation.symbol(), new MethodData(o, m));
                else
                    memory.registerSubmethod(o, m);
            }
        }
    }

    public static Memory getDefaultMemory(){
        LambdaEngine.registerFunctions(new ListData());
        LambdaEngine.registerFunctions(new MapData());

        LambdaEngine.registerFunctions(new ControlFunctions());

        LambdaEngine.registerFunctions(new MathFunctions());
        LambdaEngine.registerFunctions(new DebugFunctions());
        return new Memory(memory);
    }


    public static InstructionSet compile(String code, OperatorDictionary dictionary) throws Exception {
        String lastText = "";
        List<Instruction> instructions = new LinkedList<>();
        code = code.replace(" ", "").replace("\r", "").replace("\n", "").replace("\t", "").replaceAll("(^|(?<=[,({\\[]))([-])", "0-");
        while(code.length() > 0){
            if(!code.equals(lastText)) {
                lastText = code;
                code = compileNextLine(code, instructions, dictionary);
            }else{
                throw new Exception("Syntax error.");
            }
        }
        return new InstructionSet(instructions);
    }

    public static OperatorDictionary getDefaultOperatorDictionary(){
        OperatorDictionary dictionary = new OperatorDictionary();
        dictionary.registerOperator(new BasicOperator());
        return dictionary;
    }

    private static String compileNextLine(String code, List<Instruction> instructions, OperatorDictionary dictionary) throws Exception {
        Matcher matcher = pattern.matcher(code);

        List<Token> pl = new ArrayList<>();
        Stack<Token> ps = new Stack<>();

        int depth = 0;

        while(code.length() > 0 && !code.startsWith(";")){
            String sub = null;
            String v;
            Token t = null;
            if(matcher.find()) {
                v = matcher.group();
                sub = matcher.replaceFirst("");
                switch (v) {
                    case "(":
                        depth++;
                        break;
                    case ")":
                        depth--;
                        break;
                    case ",":
                        t = new OperatorToken(",", null, null, 0, 0, depth);
                        break;
                    default:
                        if(v.matches("^[A-Za-z_.]+$") && sub.length() > 0 && sub.charAt(0) == '(') {
                            t = new VariableOperator(v, depth);
                        }else if(dictionary.containsOperator(v)){
                            t = dictionary.getOperator(v, depth);
                        }else if (v.matches("^[0-9.]+$")) {
                            t = new NumberData(Double.parseDouble(v));
                        } else if (v.startsWith("\"")) {
                            t = new StringData(v.substring(1, v.length() - 1));
                        }else{
                            t = new VariableData(v);
                        }
                        break;
                }
            }else if(code.startsWith("{")){
                int bracketDepth = 1;
                boolean insideString = false;
                boolean ignorenext = false;
                int i = 1;
                for(; i < code.length() && bracketDepth > 0; i++){
                    if(!ignorenext) {
                        char c = code.charAt(i);
                        if (!insideString) {
                            if (c == '{') bracketDepth++;
                            if (c == '}') bracketDepth--;
                        }
                        if (c == '"') insideString = !insideString;
                        if (c == '\\') ignorenext = true;
                    }else ignorenext = false;
                }
                v = code.substring(0, i);
                sub = code.replace(v, "");
                v = v.substring(1, v.length()-1);
                t = new CodeData(LambdaEngine.compile(v, dictionary));
            }else if(code.startsWith("[")){
                int bracketDepth = 1;
                boolean insideString = false;
                boolean ignorenext = false;
                int i = 1;
                for(; i < code.length() && bracketDepth > 0; i++){
                    if(!ignorenext) {
                        char c = code.charAt(i);
                        if (!insideString) {
                            if (c == '[') bracketDepth++;
                            if (c == ']') bracketDepth--;
                        }
                        if (c == '"') insideString = !insideString;
                        if (c == '\\') ignorenext = true;
                    }else ignorenext = false;
                }
                v = code.substring(0, i);
                sub = code.replace(v, "");
                sub = "list("+v.substring(1,i-1)+")"+sub;

            }

            if (!ps.empty() && ps.peek().getDepth() > depth)
                while (!ps.empty() && ps.peek().getDepth() > depth)
                    pl.add(ps.pop());
            if (t != null) {
                Class<?> type;
                if (t instanceof DataToken)  type = DataToken.class;
                else                         type = OperatorToken.class;

                if (type == DataToken.class) {
                    pl.add(t);
                } else {
                    if (!ps.empty()) {
                        OperatorToken ot = (OperatorToken) t;
                        OperatorToken sot = (OperatorToken) ps.peek();
                        if (ps.peek().getDepth() == t.getDepth() && sot.getPriority() > ot.getPriority())
                            while (!ps.empty() && ps.peek().getDepth() == depth && ((OperatorToken) ps.peek()).getPriority() > ot.getPriority()) {
                                pl.add(ps.pop());
                            }
                    }
                    ps.push(t);
                }
            }

            matcher = pattern.matcher(sub);
            code = sub;
        }

        while (!ps.empty())
            pl.add(ps.pop());

        if(code.startsWith(";")) code = code.substring(1);
        instructions.add(new Instruction(pl));
        return code;
    }
}
