package com.springboot.booking.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HandlerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HandlerException(String message) {
        super(message);
    }
}
