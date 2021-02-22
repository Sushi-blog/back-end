package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class NicknameAlreadyExistsException extends SushiException {
    public NicknameAlreadyExistsException() {
        super(ErrorCode.ALREADY_EXIST);
    }
}
