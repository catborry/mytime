package top.yyqdemao.mytime.utilMail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Component
@Slf4j
public class Mail implements ApplicationRunner {
    @Value("${lance.mail.sender}")
    private  String MAIL_SENDER;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String sdate = "2019-04-02 07:00:00";
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = sf.parse(sdate);
        Timer t=new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    //邮件发送人
                    simpleMailMessage.setFrom(MAIL_SENDER);
//                    //邮件接收人

//                    simpleMailMessage.setTo("2591981044@qq.com");
                    simpleMailMessage.setTo("2591981044@qq.com","2270989618@qq.com");
                    //邮件主题
                    simpleMailMessage.setSubject("起床啦!!!!");
                    StringBuilder sb=new StringBuilder();
                    LocalDateTime now = LocalDateTime.now();
                    sb.append("现在时间是"+now.getYear()+"年"+now.getMonthValue()+"月"+now.getDayOfMonth()+"日  "+now.getHour()+"点"+now.getMinute()+"分"+now.getSecond()+"秒");
                    //邮件内容
                    simpleMailMessage.setText(sb+eat());
                    javaMailSender.send(simpleMailMessage);
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
        sb.append("今天早上适合喝");
        sb.append(drink[r.nextInt(drink.length)]);
        sb.append(", 主食适合");
        sb.append(eat[r.nextInt(eat.length)]);
        return sb.toString();
    }
}
