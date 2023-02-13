package com.genshin.exception;

public class ArtifactParseException extends RuntimeException{
    public ArtifactParseException(){
        super("Artifact parse error");
    }
}
