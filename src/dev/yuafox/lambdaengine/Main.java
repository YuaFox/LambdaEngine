package dev.yuafox.lambdaengine;

import dev.yuafox.lambdaengine.engine.LambdaEngine;
import dev.yuafox.lambdaengine.engine.InstructionSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()){
            sb.append(scanner.nextLine());
        }
        InstructionSet compile = LambdaEngine.compile(sb.toString(), LambdaEngine.getDefaultOperatorDictionary());
        compile.run(LambdaEngine.getDefaultMemory(), null, false);
    }
}