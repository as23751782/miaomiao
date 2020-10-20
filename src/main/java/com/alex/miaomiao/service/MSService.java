package com.alex.miaomiao.service;

import com.alex.miaomiao.config.MsLog;
import com.alex.miaomiao.config.UrlConstant;
import com.alex.miaomiao.config.Util;
import com.alex.miaomiao.vo.Area;
import com.alex.miaomiao.vo.MSRequest;
import com.alex.miaomiao.vo.UserInfo;
import com.alex.miaomiao.vo.VaccineInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public void startMS(MSRequest request) {
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
        vaccineList.forEach(vaccineInfo -> log.debug("\t\u25CF {}", vaccineInfo));

        //4、get member
        UserInfo user = getMember();
        log.fmtDebug("接种人姓名：[{}]，身份证号：[{}]，ID：[{}]", user.getName(), user.getIdCardNo(), user.getId());

        //5、start task
        log.fmtDebug("启动秒杀任务，敬请期待...");
//        serviceExecutor.execute(() -> {});

    }

    private String ms(String msId, String linkmanId, String idCardNo) {
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
            if ("0000".equals(response.get("code"))) {
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

    private String getSt(String msId) {
        HttpEntity<Object> httpEntity = Util.getHttpEntity();
        Map<String, String> param = new HashMap<>();
        param.put("id", msId);

        String res = get(UrlConstant.ST, httpEntity, param);
        log.info("st: {}", res);
        return res;
    }


}

class EmptyMap<K, V> implements Map<K, V> {
    public int size() { return 0; }
    public boolean isEmpty() { return true; }
    public boolean containsKey(Object key) { return false; }
    public boolean containsValue(Object value) { return false; }
    public V get(Object key) { return null; }
    public V put(K key, V value) { return null; }
    public V remove(Object key) { return null; }
    public void putAll(Map<? extends K, ? extends V> m) { }
    public void clear() { }
    public Set<K> keySet() { throw new UnsupportedOperationException(); }
    public Collection<V> values() { throw new UnsupportedOperationException(); }
    public Set<Entry<K, V>> entrySet() { throw new UnsupportedOperationException(); }
}
