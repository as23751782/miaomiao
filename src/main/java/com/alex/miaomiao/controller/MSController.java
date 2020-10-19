package com.alex.miaomiao.controller;

import com.alex.miaomiao.service.MSService;
import com.alex.miaomiao.vo.MSRequest;
import com.alex.miaomiao.vo.MSResponse;
import com.alex.miaomiao.vo.UserInfo;
import com.alex.miaomiao.vo.VaccineInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "秒杀")
@Controller
@RequestMapping("/ms")
public class MSController {

    @Resource
    private MSService service;

    @ApiOperation("设置token")
    @PostMapping(value = "/login")
    @ResponseBody
    public MSResponse<String> get(@RequestBody String token) {
        service.login(token);
        return new MSResponse<>();
    }

    @ApiOperation("查看列表")
    @GetMapping(value = "/list/{regionCode}")
    @ResponseBody
    public MSResponse<List<VaccineInfo>> list(@PathVariable("regionCode") String regionCode) {
        return new MSResponse<>(service.getList(regionCode));
    }

    @ApiOperation("查看联系人")
    @GetMapping(value = "/member")
    @ResponseBody
    public MSResponse<UserInfo> member() {
        return new MSResponse<>(service.getMember());
    }

    @ApiOperation("获取服务器时间")
    @GetMapping(value = "/now")
    @ResponseBody
    public MSResponse<String> now() {
        return new MSResponse<>(service.now());
    }

    @ApiOperation("开启秒杀任务")
    @PostMapping(value = "/start")
    @ResponseBody
    public MSResponse<String> start(@RequestBody MSRequest request) {
        service.startMS(request);
        return new MSResponse<>();
    }


}
