package com.alex.miaomiao.vo;

import lombok.Data;

@Data
public class UserInfo {
    private Integer id;
    private String name;
    private String idCardNo;

    @Override
    public String toString() {
        return "{" + "\"id\":" +
                id +
                ",\"name\":\"" +
                name + '\"' +
                ",\"idCardNo\":\"" +
                idCardNo + '\"' +
                '}';
    }
}
