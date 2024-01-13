package com.springboot.booking.exeption;

import com.springboot.booking.common.ExceptionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public GlobalException(final String message) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.message = message;
    }

    public GlobalException(final ExceptionResult result) {
        this.code = result.getCode();
        this.message = result.getMessage();
    }

    public GlobalException(final ExceptionResult result, String ... params) {
        this.code = result.getCode();
        this.message = String.format(result.getMessage(), params);
    }

    public GlobalException(final ExceptionResult result, Exception e) {
        super(e);
        this.code = result.getCode();
        this.message = result.getMessage();
    }
}
