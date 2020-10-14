package com.alex.miaomiao.controller;

import com.alex.miaomiao.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService service;

    @RequestMapping("")
    public String hello() {
        return "hello world~";
    }

    @GetMapping(value = "/app/{appId}")
    @ResponseBody
    public String get(@PathVariable("appId") String appId) {
//        String token = "3350150c831748679f2930c04a1eb8c2";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsdW9wYW4iLCJpZCI6IjhhNDVmMWVjMzFkODQ0MTFiOTc4MWVkZmMxM2NhNTA0Iiwicm9sZUlkIjoiMSIsInJvbGVUeXBlIjoiMSIsInNjb3BlcyI6WyJST0xFX09QX0FETUlOIl0sImlzcyI6IkdIQ0EiLCJpYXQiOjE2MDI1ODMwNTYsImV4cCI6MTYwMjU4NDg1Nn0.oQbKQk-elKjEO4PVcqIA8tiSCEvq3Bgypf0IvNbvE052a13WkC5X-IKRIlNW-JIcgalNY5XWEyH-OJt9cLBOTQ";
        String url = "http://10.143.133.190:28036/vdc/api/1.0/app/{id}";
        return service.get(token, url, appId);
    }

}
