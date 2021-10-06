package dev.yuafox.lambdaengine.token;

public abstract class Token {

    private int depth;

    public Token(int depth){
        this.depth = depth;
    }

    public int getDepth(){ return depth; }
}
