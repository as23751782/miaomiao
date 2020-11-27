package com.alex.miaomiao.service;

import com.alex.miaomiao.config.UrlConstant;
import com.alex.miaomiao.config.Util;
import com.alex.miaomiao.vo.UserInfo;
import com.alex.miaomiao.vo.VaccineInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SubscribeService {
    private static final Logger logger = LoggerFactory.getLogger(SubscribeService.class);
    private static final boolean DEBUG = false;

    @Resource
    private RestTemplate restTemplate;

    @Resource(name = "mServiceExecutor")
    private ThreadPoolTaskExecutor serviceExecutor;

    public List<VaccineInfo> listVacc(String regionCode) {
        return doHttpGetList(UrlConstant.LIST, Util.getHttpEntity(), VaccineInfo.class, regionCode);
    }

    public List<UserInfo> listUsers() {
        return doHttpGetList(UrlConstant.LINKMAN, Util.getHttpEntity(), UserInfo.class);
    }

    public Long serverTime() {
        return doHttpGetObj(UrlConstant.NOW, Util.getHttpEntity(), Long.class);
    }

    public void subscribe() {
        // 1、拿列表，解析时间
        logger.debug("获取疫苗信息：");
        List<VaccineInfo> vaccList = listVacc(Util.REGION);
        if (vaccList == null || vaccList.size() == 0) {
            logger.debug("当前区域：{}下无疫苗信息...", Util.REGION);
            return;
        }
        vaccList.forEach(vaccineInfo -> logger.debug("\t开始时间：{}，\t库存：{}，\t地址：{}", vaccineInfo.getStartTime(), vaccineInfo.getStock(), vaccineInfo.getName()));

        // 2、设置接种人
        logger.debug("获取接种人信息：");
        List<UserInfo> userList = listUsers();
        if (userList == null || userList.size() == 0) {
            logger.debug("接种人信息为空...");
            return;
        }
        userList.forEach(userInfo -> logger.debug("\t{}接种人：{}，\t身份证号：{}", 1 == userInfo.getIsDefault() ? "\u27A3 " : "", userInfo.getName(), userInfo.getIdCardNo()));
        UserInfo user = userList.stream().filter(userInfo -> userInfo.getIsDefault() == 1).findFirst().orElse(null);
        if (user == null) {
            logger.debug("未找到默认接种人：{}", userList);
            return;
        }
        logger.debug("选取默认接种人：[{}]", user.getName());

        // 3、获取当前服务器时间，设置秒杀任务
        long serverTime = serverTime();
        long localTime = System.currentTimeMillis();
        long delta = serverTime - localTime;
        logger.debug("本地与秒杀服务器时间差：{}", delta);

        //本地+delta=服务器时间
        //delta小于0，服务器时间比本地慢，本地需要提前
        AtomicBoolean success = new AtomicBoolean(false);
        AtomicReference<String> orderId = new AtomicReference<>(null);

        //


    }

    private String ms(String vaccId, AtomicBoolean success) {

        success.set(true);
        return "";
    }

    private Runnable getTask(long sleep, String vaccId, AtomicBoolean success, AtomicReference<String> orderId) {
        return () -> {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                logger.error("启动线程睡眠异常：{}", e.getMessage());
                return;
            }

            long start = System.currentTimeMillis();
            do {
                try {
                    orderId.set(ms(vaccId, success));
                    logger.info("秒杀成功，订单ID：{}", orderId.get());
                } catch (Exception e) {
                    logger.error("秒杀失败，睡眠100ms，{}", e.getMessage());
                }

                long now = System.currentTimeMillis();
                if (now >= (start + 1000 * 120)) {
                    logger.info("时间到，结束秒杀");
                    return;
                }

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException interruptedException) {
                    logger.error("睡眠异常：{}", interruptedException.getMessage());
                    return;
                }
            } while (success.get());
        };
    }

    private <T> List<T> doHttpGetList(String url, HttpEntity<Object> httpEntity, Class<T> responseType, Object... uriVariables) {
        JSONObject jsonObject = doHttpGet(url, httpEntity, uriVariables);
        if (jsonObject == null) {
            logger.error("请求失败，服务器无响应");
            return null;
        }
        if (!"0000".equals(jsonObject.getString("code"))) {
            // eg: {"code":"1001","msg":"非法请求,请登录授权后访问!","notOk":true,"ok":false}
            logger.error("请求失败：{}", jsonObject.toString());
            return null;
        }
        if (DEBUG) {
            logger.debug("响应信息：{}", jsonObject.toString());
        }

        return JSON.parseArray(jsonObject.getJSONArray("data").toJSONString(), responseType);
    }

    private <T> T doHttpGetObj(String url, HttpEntity<Object> httpEntity, Class<T> responseType) {
        JSONObject jsonObject = doHttpGet(url, httpEntity);
        if (jsonObject == null) {
            logger.error("请求失败，服务器无响应");
            return null;
        }
        if (!"0000".equals(jsonObject.getString("code"))) {
            // eg: {"code":"1001","msg":"非法请求,请登录授权后访问!","notOk":true,"ok":false}
            logger.error("请求失败：{}", jsonObject.toString());
            return null;
        }
        if (DEBUG) {
            logger.debug("响应信息：{}", jsonObject.toString());
        }
        return jsonObject.getObject("data", responseType);
    }

    private JSONObject doHttpGet(String url, HttpEntity<Object> httpEntity, Object... uriVariables) {
        String response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, uriVariables).getBody();
        return response == null ? null : JSONObject.parseObject(response);
    }

}
