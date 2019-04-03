package top.yyqdemao.mytime.utilMail;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import top.yyqdemao.mytime.api.Weather;
import top.yyqdemao.mytime.api.util.WeatherSay;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class Mail implements ApplicationRunner {
    @Value("${lance.mail.sender}")
    private  String MAIL_SENDER;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    Weather weather;
    @Autowired
    WeatherSay weatherSay;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String sdate = "2019-04-01 10:20:00";
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = sf.parse(sdate);
        Timer t=new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    //复杂邮件
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    //邮件发送助手
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    //邮件设置
                    helper.setSubject("起床啦!!!");
                    //接收者
                    String[] to={"2591981044@qq.com","2270989618@qq.com"};
                    helper.setTo(to);
                    //发送者
                    helper.setFrom(MAIL_SENDER);
                    //邮件主题
                    StringBuilder sb=new StringBuilder();
                    LocalDateTime now = LocalDateTime.now();
                    sb.append("<div> 现在时间是"+now.getYear()+"年"+now.getMonthValue()+"月"+now.getDayOfMonth()+"日  "+now.getHour()+"点"+now.getMinute()+"分"+now.getSecond()+"秒</div></br>");
                    //使用 HTML 格式，true
                    helper.setText(sb+eat()+weaterChange(),true);
                    javaMailSender.send(mimeMessage);
                    log.info("发送成功");
                } catch (Exception e) {
                    log.error("邮件发送失败", e.getMessage());
                }
//                86400000
            }
        //86400000
        },date,86400000);
    }

    public String eat(){
        String[] drink={"牛奶","酸奶","热水","豆浆","早餐奶"};
        String[] eat={"饭","米粉","包子","油条","饼干"};
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        sb.append("<div>今天早上适合喝");
        sb.append("<span style=\"color: red;\">");
        sb.append(drink[r.nextInt(drink.length)]);
        sb.append("</span>");
        sb.append(", 主食适合");
        sb.append("<span style=\"color: red;\">");
        sb.append(eat[r.nextInt(eat.length)]);
        sb.append("</span>"+"</div></br>");
        return sb.toString();
    }
    public String weaterChange(){
        String weatherapi = weather.weatherapi();
        JSONObject jsonObject=new JSONObject(weatherapi);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject city = data.getJSONObject("city");
        //省
        String pname = city.getString("pname");
        //市
        String name = city.getString("name");
        JSONObject condition = data.getJSONObject("condition");
        //天气
        String condition1 = condition.getString("condition");
        //湿度
        String humidity = condition.getString("humidity");
        //温度
        int temp = condition.getInt("temp");
        //发布时间
        String updatetime = condition.getString("updatetime");
        //风向
        String windDir = condition.getString("windDir");
        //风级
        String windLevel = condition.getString("windLevel");
        StringBuilder sb=new StringBuilder();
        sb.append("     <div> "+pname+name+"今天的天气是:<span style=\"color: red;\">"+condition1+" </span></br> ");
        sb.append("<div><span style=\"color: red;\">");
        String say = weatherSay.getSay(condition1);
        sb.append(say);
        sb.append("</span></div></br>");
        sb.append("         湿度是:"+humidity+"   "+windLevel+"级"+windDir+"  温度是:"+temp+" </br> ");
        sb.append("<span style=\"color: red;\">");
        if (temp<0){
            sb.append("天气很冷,衣服加没,不许感冒");
        }else if(temp>0 && temp<20){
            sb.append("天气有点冷,注意加衣服哦");
        }else if(temp>20 && temp<25){
            sb.append("这个温度很舒适呢,注意不要着凉哦");
        }else if(temp>25 && temp<35){
            sb.append("有点热,不要中暑了");
        }else{
            sb.append("会热死去,不要出门啦");
        }
        sb.append("</span></div>");
        return sb.toString();
    }
}
