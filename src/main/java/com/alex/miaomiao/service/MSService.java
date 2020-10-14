package com.alex.miaomiao.service;

import com.alex.miaomiao.config.UrlConstant;
import com.alex.miaomiao.config.Util;
import com.alex.miaomiao.vo.UserInfo;
import com.alex.miaomiao.vo.VaccineInfo;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MSService {

    @Resource
    private RestTemplate restTemplate;

//    public String login(String code) {
//        Map<String, String> map = new HashMap<>();
//        map.put("Content-Type", "application/x-www-form-urlencoded");
//        map.put("tk", "false");
//        HttpEntity<String> httpEntity = Util.getHttpEntity(map);
//        Map<String, String> param = new HashMap<>();
//        param.put("code", code);
//        param.put("minaId", "10");
//
//        ResponseEntity<MSResponse> response = restTemplate.exchange(UrlConstant.LOGIN, HttpMethod.GET, httpEntity, MSResponse.class, param);
//        LoginVo loginVo = (LoginVo) response.getBody().getData();
//        log.debug("login success: {}", loginVo);
//        return "";
//    }

    public void getList(String regionCode) {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        Map<String, String> param = new HashMap<>();
        param.put("regionCode", regionCode);

        List<VaccineInfo> vaccineList = Util.getListFromJson(get(UrlConstant.LIST, httpEntity, param), VaccineInfo.class);
        vaccineList.forEach(entity -> log.info("vaccine: {}", entity));
    }

    public void getMember() {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        UserInfo linkman = Util.getListFromJson(get(UrlConstant.LINKMAN, httpEntity, Collections.EMPTY_MAP), UserInfo.class).get(0);

    }

    public String getSt(String msId) {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        Map<String, String> param = new HashMap<>();
        param.put("id", msId);

        String res = get(UrlConstant.ST, httpEntity, param);
        log.info("st: {}", res);
        return res;
    }

    public String ms(String msId, String linkmanId, String idCardNo) {
        String md5 = Util.hexMD5(msId, getSt(msId));
        Map<String, String> map = new HashMap<>();
        map.put("ecc-hs", md5);
        HttpEntity<Object> httpEntity = Util.getHttpEntity(map);

        Map<String, String> param = new HashMap<>();
        param.put("seckillId", msId);
        param.put("linkmanId", linkmanId);
        param.put("idCardNo", idCardNo);
        param.put("vaccineIndex", "1");

        JSONObject response = restTemplate.exchange(UrlConstant.MS, HttpMethod.GET, httpEntity, JSONObject.class, param).getBody();
        if (response != null) {
            if("0000".equals(response.get("code"))){
                return response.getString("data");
            }
        }
        return null;
    }

    private String get(String url, HttpEntity<Object> httpEntity, Map<String, String> param) {
        JSONObject response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class, param).getBody();
        if (response != null) {
            String json = response.toJSONString();
            JSONObject jsonObject = JSONObject.parseObject(json);
            return jsonObject.getString("data");
        }
        return "{}";
    }


}
