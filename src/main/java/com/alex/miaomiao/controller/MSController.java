package com.alex.miaomiao.controller;

import com.alex.miaomiao.service.MSService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/ms")
public class MSController {

    @Resource
    private MSService service;

//    @GetMapping(value = "/login/{code}")
//    @ResponseBody
//    public String get(@PathVariable("code") String code) {
//        code = "073arl0w3MJe8V2qqG0w3UVp5I2arl0F";
//        return service.login(code);
//    }

    @GetMapping(value = "/list/{regionCode}")
    @ResponseBody
    public void list(@PathVariable("regionCode") String regionCode) {
        service.getList(regionCode);
    }
}
