package com.springboot.booking.model;

import com.springboot.booking.common.ExceptionResult;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    int errorCode;
    String errorMessage;

    public BException(final String message) {
        super();
        this.errorCode = HttpStatus.BAD_REQUEST.value();
        this.errorMessage = message;
    }

    public BException(final ExceptionResult result) {
        super();
        this.errorCode = result.getCode();
        this.errorMessage = result.getMessage();
    }

    public BException(final ExceptionResult result, Exception e) {
        super(e);
        this.errorCode = result.getCode();
        this.errorMessage = result.getMessage();
    }
}
