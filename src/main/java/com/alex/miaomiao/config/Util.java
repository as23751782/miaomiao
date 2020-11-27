package com.alex.miaomiao.config;

import com.alex.miaomiao.vo.AreaConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Util {

    public static String COOKIES = "";
    public static String TK;
    public static String REGION;
    public static Integer MEMBER_ID;
    public static HttpHeaders HEADERS;
    public static String ID_CARD;
    private static List<AreaConfig> areaList;
    private final static Map<String, AreaConfig> areaMap = new HashMap<>();

    private static HttpHeaders getHeader() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Host", "miaomiao.scmttec.com");
//        headers.set("Connection", "keep-alive");
//        headers.set("Accept", "application/json, text/plain, */*");
//        headers.set("X-Requested-With", "XMLHttpRequest");
//        headers.set("content-type", "application/json");
//        headers.set("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; SM-N960F Build/JLS36C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.136 Mobile Safari/537.36 MMWEBID/1042 MicroMessenger/7.0.15.1680(0x27000F34) Process/appbrand0 WeChat/arm32 NetType/WIFI Language/zh_CN ABI/arm32");
//        headers.set("Referer", "https://servicewechat.com/wxff8cad2e9bf18719/16/page-frame.html");
//        headers.set("Accept-Encoding", "gzip, deflate, br");
//        headers.set("Cookie", COOKIES);
//        headers.set("tk", TK);
        return HEADERS;
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

    public static String decode(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(str));
    }

    public static void main(String[] args) {
        String str = "R0VUIGh0dHBzOi8vbWlhb21pYW8uc2NtdHRlYy5jb20vc2Vja2lsbC9zZWNraWxsU3Vic2NyaWJlL3BhZ2VMaXN0LmRvP29mZnNldD0wJmxpbWl0PTEwIEhUVFAvMS4xCkhvc3Q6IG1pYW9taWFvLnNjbXR0ZWMuY29tCkNvbm5lY3Rpb246IGtlZXAtYWxpdmUKQWNjZXB0OiBhcHBsaWNhdGlvbi9qc29uLCB0ZXh0L3BsYWluLCAqLyoKQ29va2llOiBfeHhobV89JTdCJTIyaGVhZGVySW1nJTIyJTNBJTIyaHR0cCUzQSUyRiUyRnRoaXJkd3gucWxvZ28uY24lMkZtbW9wZW4lMkZRM2F1SGd6d3pNNVppY1NxM2ljWGttV0RYYWhzcWpONnZuOVpyWGFrU1NMaWF5cmx2RHhJVE81UU9YbWdSYTU3YUNPWXdOOVFpYnlyMlVySTFRNDBpYkJ1SExzQW1adEFSV0pFN2lidEU1cFBNTDk4ZyUyRjEzMiUyMiUyQyUyMm1vYmlsZSUyMiUzQSUyMjE2NioqKioyMzc1JTIyJTJDJTIybmlja05hbWUlMjIlM0ElMjIlRTQlQjglODklRTQlQjglODklMjIlMkMlMjJzZXglMjIlM0EyJTdEOyBfeHpral89d3hhcHB0b2tlbjoxMDpkM2I2ODU1M2U5NTQ0YjM1ZGMwOWE4YjE3NGQ0YmU4Zl8zY2VlM2Q4NTU3MDkxNjgxZjM2MGQyYmQzOTQzOTQ5MzsgMzVlYz1kNjlkM2RkMjllMTAyN2MyZTg7IGY5MzE9ZmE5MjRjNGVlN2M3YTgwM2JmOyA2YmJjPTM2OTVmMzIyYWEzM2QyZTQ3MzsgOGI5Mz02MmI2NTRmZTk5MWVmZTRkNTUKVXNlci1BZ2VudDogTW96aWxsYS81LjAgKFdpbmRvd3MgTlQgNi4xOyBXT1c2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzUzLjAuMjc4NS4xNDMgU2FmYXJpLzUzNy4zNiBNaWNyb01lc3Nlbmdlci83LjAuOS41MDEgTmV0VHlwZS9XSUZJIE1pbmlQcm9ncmFtRW52L1dpbmRvd3MgV2luZG93c1dlY2hhdApYLVJlcXVlc3RlZC1XaXRoOiBYTUxIdHRwUmVxdWVzdApjb250ZW50LXR5cGU6IGFwcGxpY2F0aW9uL2pzb24KdGs6IHd4YXBwdG9rZW46MTA6ZDNiNjg1NTNlOTU0NGIzNWRjMDlhOGIxNzRkNGJlOGZfM2NlZTNkODU1NzA5MTY4MWYzNjBkMmJkMzk0Mzk0OTMKUmVmZXJlcjogaHR0cHM6Ly9zZXJ2aWNld2VjaGF0LmNvbS93eGZmOGNhZDJlOWJmMTg3MTkvMTYvcGFnZS1mcmFtZS5odG1sCkFjY2VwdC1FbmNvZGluZzogZ3ppcCwgZGVmbGF0ZSwgYnIKCg==";
        setHeader(str);
        getHeader().forEach((s, list) -> System.out.println(s + " ==> " + list));
    }

    public static void setHeader(String decodeStr) {
        String[] headerStr = new String(Base64.getDecoder().decode(decodeStr)).split("\n");

        HEADERS = new HttpHeaders();
        for (String str : headerStr) {
            if (str.contains("GET https"))
                continue;

            String[] arr = str.split(":");

            if ("Cookie".equalsIgnoreCase(arr[0]) ||
                    "tk".equalsIgnoreCase(arr[0]) ||
                    "Referer".equalsIgnoreCase(arr[0])) {

                int start = str.indexOf(":");
                String s = str.substring(start + 1);
                HEADERS.set(arr[0].trim(), s.trim());

            } else {
                HEADERS.set(arr[0].trim(), arr[1].trim());
            }
        }
    }

}
