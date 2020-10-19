package com.alex.miaomiao.vo;

import java.io.Serializable;

public class MSResponse<T> implements Serializable {
    private static final long serialVersionUID = 5392164784761709333L;

    private String code;
    private String message;
    private T data;

    public MSResponse() {
        this.code = "200";
        this.message = "success";
    }

    public MSResponse(T data) {
        this.code = "200";
        this.message = "success";
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
