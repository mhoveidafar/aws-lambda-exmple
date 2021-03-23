package com.mhoveidafar.stock.exception;

import lombok.Getter;

// instead of writing getters method
@Getter
public class ValidationException extends RuntimeException{

    private int status;
    private String reason;

    public ValidationException (String exceptionReason) {
        status = 400;
        reason = exceptionReason;
    }
}
