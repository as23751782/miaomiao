package com.alex.miaomiao.config;

import com.alex.miaomiao.vo.Area;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;


public class Util {

//    public static String COOKIES = "";
    public static String TK;
    public static Integer MEMBER_ID;


    private static HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Host", "miaomiao.scmttec.com");
        headers.set("Connection", "keep-alive");
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("content-type", "application/json");
        headers.set("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; SM-N960F Build/JLS36C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.136 Mobile Safari/537.36 MMWEBID/1042 MicroMessenger/7.0.15.1680(0x27000F34) Process/appbrand0 WeChat/arm32 NetType/WIFI Language/zh_CN ABI/arm32");
        headers.set("Referer", "https://servicewechat.com/wxff8cad2e9bf18719/14/page-frame.html");
        headers.set("Accept-Encoding", "gzip, deflate, br");
//        headers.set("Cookie", COOKIES);
        headers.set("tk", TK);
        return headers;
    }

    public static <T> HttpEntity<T> getHttpEntity() {
        return getHttpEntity(null);
    }
    public static <T> HttpEntity<T> getHttpEntity(Map<String, String> map) {
        HttpHeaders headers = getHeader();
        if (map != null) {
            map.forEach(headers::set);
        }
        return new HttpEntity<>(headers);
    }

    public static String hexMD5(String seckillId, String st) {
        String salt = "ux$ad70*b";
        final Integer memberId = MEMBER_ID;
        String md5 = DigestUtils.md5Hex(seckillId + st + memberId);
        return DigestUtils.md5Hex(md5 + salt);
    }

    public static <T> List<T> getListFromJson(String json, Class<T> cls) {
        return JSON.parseArray(json, cls);
    }

    public static <T> T getObjectFromJson(String json, Class<T> cls) {
        System.out.println();
        System.out.println();
        System.out.println(json);
        System.out.println();
        System.out.println();
        return JSON.parseObject(json, cls);
    }

    public static Area getArea(String regionCode) {
        return new Area();
    }

}
