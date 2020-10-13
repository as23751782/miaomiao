package com.alex.miaomiao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

//@Slf4j
@Service
public class TestService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

    @Resource
    private RestTemplate restTemplate;

    public String get(String token, String url, String appId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, appId);
        LOGGER.info("http response: {}", response);
        return response.getBody();
    }
}
