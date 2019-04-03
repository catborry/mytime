package top.yyqdemao.mytime.api;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.yyqdemao.mytime.api.util.HttpUtils;

import java.util.HashMap;
import java.util.Map;
@Component
public class Weather {

    @Value("${weather.appcode}")
    private String appcode;

    public String weatherapi() {
        String host = "http://freecityid.market.alicloudapi.com";
        String path = "/whapi/json/alicityweather/briefcondition";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("cityId", "650");
//        bodys.put("token", "93e800e21280c65f4aa0d236ddf634c1");
        String json=null;
        HttpResponse response=null;
        try {
             response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            json = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return json;
        }
    }
}
