package com.xaviervinicius.labschedule.exceptions;

public class NoSuchVerificationCodeException extends RuntimeException {
    public NoSuchVerificationCodeException(String message) {
        super(message);
    }
}
