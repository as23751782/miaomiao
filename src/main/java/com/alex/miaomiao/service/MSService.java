package com.alex.miaomiao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class MSService {

    @Resource
    private RestTemplate restTemplate;



}
