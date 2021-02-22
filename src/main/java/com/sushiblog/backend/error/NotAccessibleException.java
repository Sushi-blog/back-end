package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class NotAccessibleException extends SushiException {
    public NotAccessibleException() {
        super(ErrorCode.NOT_ACCESSIBLE);
    }
}
