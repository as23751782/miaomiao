package com.alex.miaomiao.exception;

public class MSException extends RuntimeException {
    private final String code;

    private final String errMsg;

    public MSException(String code, String message) {
        super(message);
        this.code = code;
        this.errMsg = message;
    }

    public String getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
