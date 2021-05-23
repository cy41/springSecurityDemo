package com.example.securitydemo.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseData {
    private int code;
    private String message;
    private Object data;

    public static BaseResponseData success(Object data) {
        return new BaseResponseData(SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static BaseResponseData failed(Object data) {
        return new BaseResponseData(FAILED_CODE, DEFAULT_FAILED_MESSAGE, data);
    }

    public static final int SUCCESS_CODE = 200;
    public static final String DEFAULT_SUCCESS_MESSAGE = "success";
    public static final String DEFAULT_FAILED_MESSAGE = "failed";
    public static final int FAILED_CODE = -1;
}
