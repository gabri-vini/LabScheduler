package com.xaviervinicius.labschedule.exceptions;

public class TooManyVerificationCodeRequestsException extends RuntimeException {
    public TooManyVerificationCodeRequestsException(String message) {
        super(message);
    }
}
