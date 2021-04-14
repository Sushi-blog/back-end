package com.sushiblog.backend.error.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
@Getter
@AllArgsConstructor
public enum ErrorCode { //대충 쓰면 나중에 오류날 때 어떤 오류인지 확인하기 어렵다.
    INVALID_TOKEN(400, "USER400-0", "Invalid Token"),
    NOT_FOUND(404, "USER404-0","Not Found"),
    EXPIRED_TOKEN(401, "USER401-0","Token is Expired"),
    UNAUTHENTICATED(401, "USER401-1", "UnAuthenticated"),
    NOT_ACCESSIBLE(401, "USER401-2","Check the token"),
    ALREADY_EXIST(409, "USER409-0","Already exists"),
    FORMAT_INCORRECT(400, "USER400-1", "String format is incorrect"),
    IMAGE_NOT_FOUND(404, "IMAGE404-1","Image is not found");

    private final int status;
    private final String code;
    private final String message;
}
