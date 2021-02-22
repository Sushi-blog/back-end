package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class EmailAlreadyExistsException extends SushiException {
    public EmailAlreadyExistsException() {
        super(ErrorCode.ALREADY_EXIST);
    }
}
