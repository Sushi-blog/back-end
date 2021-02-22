package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class InvalidTokenException extends SushiException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
