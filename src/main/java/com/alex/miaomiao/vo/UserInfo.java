package com.alex.miaomiao.vo;

import lombok.Data;

@Data
public class UserInfo {
    private Integer id;
    private String name;
    private String idCardNo;
    private Integer isDefault;

//    @Override
//    public String toString() {
//        return "{" + "\"id\":" +
//                id +
//                ",\"name\":\"" +
//                name + '\"' +
//                ",\"idCardNo\":\"" +
//                idCardNo + '\"' +
//                '}';
//    }


    @Override
    public String toString() {
        return "{"
                + "\"id\":"
                + id
                + ",\"name\":\""
                + name + '\"'
                + ",\"idCardNo\":\""
                + idCardNo + '\"'
                + ",\"isDefault\":"
                + isDefault
                + "}";
    }
}
