package com.alex.miaomiao.service;

import com.alex.miaomiao.config.EmptyMap;
import com.alex.miaomiao.config.MsLog;
import com.alex.miaomiao.config.UrlConstant;
import com.alex.miaomiao.config.Util;
import com.alex.miaomiao.exception.MSException;
import com.alex.miaomiao.vo.Area;
import com.alex.miaomiao.vo.MSRequest;
import com.alex.miaomiao.vo.UserInfo;
import com.alex.miaomiao.vo.VaccineInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MSService {

    private static final Map<String, String> EMPTY_MAP = new EmptyMap<>();
    private final MsLog log = MsLog.getLog(MSService.class);

    @Resource
    private RestTemplate restTemplate;

    @Resource(name = "mServiceExecutor")
    private ThreadPoolTaskExecutor serviceExecutor;

    /**
     * set login token
     *
     * @param token
     */
    public void login(String token) {
        Util.TK = token;
    }

    public String now() {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        return get(UrlConstant.NOW, httpEntity, EMPTY_MAP);
    }

    /**
     * get vaccine list
     *
     * @param regionCode
     * @return
     */
    public List<VaccineInfo> getList(String regionCode) {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        Map<String, String> param = new HashMap<>();
        param.put("regionCode", regionCode);

        return Util.getListFromJson(get(UrlConstant.LIST, httpEntity, param), VaccineInfo.class);
    }

    /**
     * get contact
     */
    public UserInfo getMember() {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        return Util.getListFromJson(get(UrlConstant.LINKMAN, httpEntity, EMPTY_MAP), UserInfo.class).get(0);
    }

    public void startMS(MSRequest request) throws InterruptedException {
        System.out.println("============================== 9价HPV疫苗秒杀脚本 ==============================");
        log.fmtDebug("开始启动脚本...");

        //1、get server time
        long serverDate = Long.parseLong(now());
        long localDate = System.currentTimeMillis();
        long delta = serverDate - localDate;
        log.fmtDebug("本地与秒杀服务器时间差：{}", delta);

        //2、login
        login(request.getToken());
        log.fmtDebug("设置登录Token...");

        //3、get vacc list
        Area area = Util.getArea(request.getRegionCode());
        log.fmtDebug("获取疫苗信息，{}-{}，区域ID：{}", area.getProvince(), area.getCity(), area.getRegionCode());
        List<VaccineInfo> vaccineList = getList(request.getRegionCode());
        log.fmtDebug("查询到{}条待秒杀信息：", vaccineList.size());
        if (vaccineList.isEmpty()) {
            return;
        }
        long startDate = convertDateToLong("2020-10-20 15:24:15");
//        long startDate = convertDateToLong(vaccineList.get(0).getStartTime());
        vaccineList.forEach(vaccineInfo -> log.debug("\t\u25CF {}", vaccineInfo));

        //4、get member
        UserInfo user = getMember();
        log.fmtDebug("接种人姓名：[{}]，身份证号：[{}]，ID：[{}]", user.getName(), user.getIdCardNo(), user.getId());
        Util.MEMBER_ID = user.getId();
        Util.ID_CARD = user.getIdCardNo();

        //5、start task
        log.fmtDebug("启动秒杀任务，敬请期待...");
        AtomicBoolean success = new AtomicBoolean(false);
        AtomicReference<String> orderId = new AtomicReference<>(null);
        List<Runnable> taskPool = vaccineList.stream().map(vaccineInfo -> getTask(vaccineInfo.getId(), vaccineInfo.getStartTime(), delta, success, orderId)).collect(Collectors.toList());

        long now = System.currentTimeMillis();
        if (now + 2000 < startDate) {
            log.info("还未到开始时间，等待中......");
            Thread.sleep(startDate - now - 2000);
        }

        //提前2000毫秒开始秒杀
        log.info("###########提前2秒 开始秒杀###########");
        startTask(taskPool);

        //提前1000毫秒开始秒杀
        do {
            now = System.currentTimeMillis();
        } while (now + 1000 < startDate);
        log.info("###########第一波 开始秒杀###########");
        startTask(taskPool);

        //提前500毫秒开始秒杀
        do {
            now = System.currentTimeMillis();
        } while (now + 500 < startDate);
        log.info("###########第二波 开始秒杀###########");
        startTask(taskPool);

        //提前200毫秒开始秒杀
        do {
            now = System.currentTimeMillis();
        } while (now + 200 < startDate);
        log.info("###########第三波 开始秒杀###########");
        startTask(taskPool);

        //准点（提前20毫秒）秒杀
        do {
            now = System.currentTimeMillis();
        } while (now + 20 < startDate);
        log.info("###########第四波 开始秒杀###########");
        startTask(taskPool);
    }

    private String ms(String msId, String linkmanId, String idCardNo, long delta) throws InterruptedException {
        String md5 = Util.hexMD5(msId, getSt(delta));
        Map<String, String> map = new HashMap<>();
        map.put("ecc-hs", md5);
        HttpEntity<Object> httpEntity = Util.getHttpEntity(map);

        Map<String, String> param = new HashMap<>();
        param.put("seckillId", msId);
        param.put("linkmanId", linkmanId);
        param.put("idCardNo", idCardNo);
        param.put("vaccineIndex", "1");

        log.fmtDebug("Request: header:{}, param:{}", httpEntity, param);
        Random random = new Random();
        int i = random.nextInt(5);
        Thread.sleep(i * 1000);
        if (random.nextInt() == 999) {
            return "order success~";
        }
        throw new MSException("404", "no response");

//        JSONObject response = restTemplate.exchange(UrlConstant.MS, HttpMethod.GET, httpEntity, JSONObject.class, param).getBody();
//        if (response != null) {
//            if ("0000".equals(response.get("code"))) {
//                return response.getString("data");
//            }
//        }
//        throw new MSException(response.getString("code"), response.getString("msg"));
    }

    private Runnable getTask(String msId, String startDate, long delta, AtomicBoolean success, AtomicReference<String> orderId) {
        long startTime = convertDateToLong(startDate);
        return () -> {
            do {
                try {
                    long id = Thread.currentThread().getId();
                    log.fmtDebug("Thread ID：{}，发送请求", id);
                    String order_id = ms(msId, Util.MEMBER_ID.toString(), Util.ID_CARD, delta);
                    orderId.set(order_id);
                    success.set(true);
                    log.fmtDebug("Thread ID：{}，抢购成功", id);
                } catch (MSException e) {
                    log.fmtDebug("Thread ID: {}, 抢购失败: {}", Thread.currentThread().getId(), e.getErrMsg());
                    if (System.currentTimeMillis() > startTime + 1000 * 60 * 3 || success.get()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.warn("Thread ID: {}，未知异常", Thread.currentThread().getId());
                }
            } while (orderId.get() == null);
        };
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

    private String getSt(String msId) {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        Map<String, String> param = new HashMap<>();
        param.put("id", msId);

        String res = get(UrlConstant.ST, httpEntity, param);
        //{"st":1603172338472,"stock":0}
        String st = JSON.parseObject(res).getString("st");
        log.info("st: {}", st);
        return st;
    }

    private String getSt(long delta) {
        long current = System.currentTimeMillis();
        return String.valueOf(current + delta);
    }

    private void startTask(List<Runnable> taskPool) {
        for (int i = 0; i < 20; i++) {
            taskPool.forEach(serviceExecutor::execute);
        }
    }

    private long convertDateToLong(String dateStr) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateStr);
            return date.getTime();
        } catch (ParseException ignored) {
        }
        return 999999999999999L;
    }
}


