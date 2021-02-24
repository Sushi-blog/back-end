package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class FormatInCorrectException extends SushiException {
    public FormatInCorrectException() {
        super(ErrorCode.FORMAT_INCORRECT);
    }
}
