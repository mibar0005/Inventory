package com.mibar.Inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//We can use the annotation @ResponseStatus at the class level in order to pass in
//an HttpStatus of NOT_FOUND along with a "reason" message.
//This will also eliminate the use for @ControllerAdvice on the ExceptionController.
//We no longer need the ExceptionController class with the @ResponseStatus bean
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value Not Found")
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
