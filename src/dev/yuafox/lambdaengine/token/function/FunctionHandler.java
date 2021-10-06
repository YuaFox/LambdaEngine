package dev.yuafox.lambdaengine.token.function;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FunctionHandler {
    String symbol();
    FunctionType type();
    int arguments() default -1;
}