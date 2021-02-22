package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class UserNotFoundException extends SushiException {
    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
