package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class BlogNotFoundException extends SushiException {
    public BlogNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
