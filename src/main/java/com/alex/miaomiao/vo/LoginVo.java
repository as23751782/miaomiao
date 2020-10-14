package com.alex.miaomiao.vo;

import lombok.Data;

/**
 * @author fengbin
 * @date 2020/10/14
 */
@Data
public class LoginVo {

    private Boolean hasLinkman;
    private Boolean hasMobile;
    private String openId;
    private Boolean register;
    private Integer st;
}
