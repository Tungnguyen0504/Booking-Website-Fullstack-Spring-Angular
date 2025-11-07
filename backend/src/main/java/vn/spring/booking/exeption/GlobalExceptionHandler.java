package vn.spring.booking.exeption;

import vn.spring.booking.model.BException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<BException> handleResourceNotFoundBException(GlobalException result) {
        result.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BException(result.getCode(), result.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BException> handleResourceNotFoundException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new BException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now()));
    }
}
