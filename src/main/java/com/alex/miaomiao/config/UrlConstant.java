package com.alex.miaomiao.config;

public class UrlConstant {

    private static final String BASE_URL = "https://miaomiao.scmttec.com";

    public static final String LOGIN = BASE_URL + "/passport/wxapp/login.do?code={code}&minaId={minaId}";
    public static final String LIST = BASE_URL + "/seckill/seckill/list.do?offset=0&limit=10&regionCode={regionCode}";
    public static final String LINKMAN = BASE_URL + "/seckill/linkman/findByUserId.do";
    public static final String MS = BASE_URL + "/seckill/seckill/subscribe.do?seckillId={seckillId}&linkmanId={linkmanId}&idCardNo={idCardNo}&vaccineIndex={vaccineIndex}";
    public static final String ST = BASE_URL + "/seckill/seckill/checkstock2.do?id={id}";
    public static final String NOW = BASE_URL + "/seckill/seckill/now2.do";
}
