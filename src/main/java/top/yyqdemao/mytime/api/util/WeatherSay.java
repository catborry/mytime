package top.yyqdemao.mytime.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@Slf4j
public class WeatherSay {
    @Value("${lance.mail.sender}")
    private  String MAIL_SENDER;
    @Autowired
    private JavaMailSender javaMailSender;
    private HashMap<String,String> map=new HashMap<>();

    {
        map.put("晴","今天有太阳呢!注意防晒哦^-^!!");
        map.put("大部晴朗","今天天气很好呢!");
        map.put("多云","今天天上很多云哦!");
        map.put("少云","你看一下是不是云很少");
        map.put("阴","是阴天呢!想不想我呀");
        map.put("阵雨","带把伞吧,雨是一阵一阵的");
        map.put("局部阵雨","你猜你那里是不是局部😂");
        map.put("小阵雨","到底是多小?");
        map.put("强阵雨","哇!带伞带伞带伞!!!");
        map.put("阵雪","下雪咯");
        map.put("雾","出门注意安全哦!");
        map.put("冻雾","这个是雾会冻起来的意思😂");
        map.put("沙尘暴","出门小心别迷了眼");
        map.put("浮尘","这种天气还是呆家里吧");
        map.put("尘卷风","呼~~呼~~呼~~");
        map.put("扬沙","出门小心别迷了眼");
        map.put("强沙尘暴","小心哦!不要被吹走了");
        map.put("霾","老老实实呆家里吧");
        map.put("冰粒","我告诉你啊不可以去尝味道,脏");
        map.put("雨夹雪","注意不要摔跤");
        map.put("小雨","今天下雨记得带伞哦");
        map.put("中雨","今天下雨记得带伞哦");
        map.put("大雨","走路小心点,不要把鞋子弄湿了");
        map.put("暴雨","不要出门了,我怕你会弄湿衣服");
        map.put("大暴雨","不要出门了,我怕你会弄湿衣服");
        map.put("特大暴雨","不要出门了,我怕你会弄湿衣服,恐怖的天气");
        map.put("小雪","下雪咯!!!");
        map.put("中雪","路面有积雪,小心摔跤");
        map.put("大雪","玩雪小心,会长冻疮");
        map.put("暴雪","玩雪小心,会长冻疮");
        map.put("冻雨","今天下雨记得带伞哦");
        map.put("雨","今天下雨记得带伞哦");
        map.put("雪","下雪咯!!!");
        map.put("雷阵雨","打雷不要怕,我在呢!!");

    }
    public String getSay(String weather){
        String s = map.get(weather);
        if(s==null){
            try {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                //邮件发送人
                simpleMailMessage.setFrom(MAIL_SENDER);
//                    //邮件接收人
                simpleMailMessage.setTo("2591981044@qq.com");
//                    simpleMailMessage.setTo("2591981044@qq.com","2270989618@qq.com");
                //邮件主题
                simpleMailMessage.setSubject("发现未知天气请及时处理");
                StringBuilder sb=new StringBuilder();
                //邮件内容
                simpleMailMessage.setText("未知天气为:"+weather);
                javaMailSender.send(simpleMailMessage);
                log.info("发送成功");
            } catch (Exception e) {
                log.error("邮件发送失败"+weather, e.getMessage());
            }
            return "发现未知天气请联系我";
        }
        return s;
    }
}
