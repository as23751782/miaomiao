package com.alex.miaomiao.vo;

import lombok.Data;

import java.util.List;

@Data
public class AreaConfig {
    private String parentName;
    private String name;
    private String value;
    private List<AreaConfig> children;

    public AreaConfig(String parentName, String name, String value) {
        this.parentName = parentName;
        this.name = name;
        this.value = value;
    }
}
