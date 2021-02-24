package com.sushiblog.backend.error.handler;

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
    NOT_ACCESSIBLE(401, "USER401-2","Check the token"),
    ALREADY_EXIST(409, "USER409-0","Already exists"),
    FORMAT_INCORRECT(400, "USER400-1", "String format is incorrect");

    private final int status;
    private final String code;
    private final String message;
}
