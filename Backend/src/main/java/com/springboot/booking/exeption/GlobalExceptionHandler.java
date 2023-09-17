package com.springboot.booking.exeption;

import com.springboot.booking.model.BException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BException.class)
    public ResponseEntity<BException> handleResourceNotFoundBException(BException result) {
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleResourceNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
