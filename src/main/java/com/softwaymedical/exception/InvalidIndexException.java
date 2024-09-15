package com.softwaymedical.exception;
/**
 * Exception lancée lorsqu'un index invalide est fourni.
 */
public class InvalidIndexException extends RuntimeException {

    public InvalidIndexException(String message) {
        super(message);
    }
}