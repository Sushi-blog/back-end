package com.sushiblog.backend.error;

import com.sushiblog.backend.error.handler.ErrorCode;
import com.sushiblog.backend.error.handler.SushiException;

public class CategoryNotFoundException extends SushiException {
    public CategoryNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
