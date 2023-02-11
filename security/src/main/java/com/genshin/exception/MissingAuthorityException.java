package com.genshin.exception;

public class MissingAuthorityException extends RuntimeException {
    public MissingAuthorityException(){
        super("Authority is missing");
    }
}
