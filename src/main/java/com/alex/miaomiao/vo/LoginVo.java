package com.alex.miaomiao.vo;

import lombok.Data;

@Data
public class LoginVo {

    private Boolean hasLinkman;
    private Boolean hasMobile;
    private String openId;
    private Boolean register;
    private Integer st;
}
