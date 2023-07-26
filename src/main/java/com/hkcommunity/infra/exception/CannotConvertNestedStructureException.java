package com.hkcommunity.infra.exception;

public class CannotConvertNestedStructureException extends RuntimeException{
    public CannotConvertNestedStructureException(String message) {
        super(message);
    }
}
