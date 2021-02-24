package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class PasswordFormatInCorrectException extends SushiException {
    public PasswordFormatInCorrectException() {
        super(ErrorCode.FORMAT_INCORRECT);
    }
}
