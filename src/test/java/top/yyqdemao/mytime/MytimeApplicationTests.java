package top.yyqdemao.mytime;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import top.yyqdemao.mytime.api.Weather;
import top.yyqdemao.mytime.api.util.WeatherSay;
import top.yyqdemao.mytime.utilMail.Mail;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MytimeApplicationTests {
    @Value("${lance.mail.sender}")
    private  String MAIL_SENDER;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    Weather weather;

    @Autowired
    WeatherSay weatherSay;

    @Autowired

    Mail mail;

    @Test
    public void contextLoads() throws JSONException {
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
            helper.setText(sb+mail.eat()+mail.weaterChange(),true);
            javaMailSender.send(mimeMessage);
            log.info("发送成功");
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }
}
