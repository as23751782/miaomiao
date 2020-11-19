package com.alex.miaomiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(simpleClientHttpRequestFactory());
//        restTemplate.setErrorHandler(responseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }
//
//    @Bean
//    public ResponseErrorHandler responseErrorHandler() {
//
//        return new ResponseErrorHandler() {
//
//            @Override
//            public boolean hasError(ClientHttpResponse response) throws IOException {
//                return true;
//            }
//
//            @Override
//            public void handleError(ClientHttpResponse response) throws IOException {
//                System.out.println(response);
//            }
//        };
//    }

}
