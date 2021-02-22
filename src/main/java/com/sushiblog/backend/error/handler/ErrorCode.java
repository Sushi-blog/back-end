package com.sushiblog.backend.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(400, "USER400-0", "Invalid Token"),
    NOT_FOUND(404, "USER404-0","Not Found"),
    EXPIRED_TOKEN(401, "USER401-0","Token is Expired"),
    UNAUTHENTICATED(401, "USER401-1", "UnAuthenticated"),
    NOT_ACCESSIBLE(401, "USER401-2","Check the token");

    private final int status;
    private final String code;
    private final String message;
}
