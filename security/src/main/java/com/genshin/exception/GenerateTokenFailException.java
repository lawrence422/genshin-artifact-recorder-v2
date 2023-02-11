package com.genshin.exception;

public class GenerateTokenFailException extends RuntimeException{
    public GenerateTokenFailException() {
        super("Generate token fail.");
    }
}
