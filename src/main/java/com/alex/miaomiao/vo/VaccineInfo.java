package com.alex.miaomiao.vo;

import lombok.Data;

@Data
public class VaccineInfo {
    private String id;
    private String name;
    private String startTime;
    private String stock;

//    private String imgUrl;
//    private String vaccineCode;
//    private String vaccineName;
//    private String address;


    @Override
    public String toString() {
        return "{" + "\"id\":\"" +
                id + '\"' +
                ",\"name\":\"" +
                name + '\"' +
                ",\"startTime\":\"" +
                startTime + '\"' +
                ",\"stock\":\"" +
                stock + '\"' +
                '}';
    }
}
