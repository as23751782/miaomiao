package com.alex.miaomiao.vo;

import lombok.Data;

@Data
public class MSResponse {
    private String code;
    private String data;
    private Boolean notOk;
    private Boolean ok;
}
