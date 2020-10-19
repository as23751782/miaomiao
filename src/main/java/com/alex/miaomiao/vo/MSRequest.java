package com.alex.miaomiao.vo;

import lombok.Data;

@Data
public class MSRequest {
    private String token;
    private String regionCode;

    @Override
    public String toString() {
        return "{" + "\"token\":\"" +
                token + '\"' +
                ",\"regionCode\":\"" +
                regionCode + '\"' +
                '}';
    }
}
