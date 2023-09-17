package com.springboot.booking.model;

import com.springboot.booking.common.SuccessResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BSuccess {
    int code;
    String message;

    public BSuccess(final SuccessResult result) {
        this.code = result.getCode();
        this.message = result.getMessage();
    }
}
