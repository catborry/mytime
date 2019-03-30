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
        String sdate = "2018-02-10 07:00:00";
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = sf.parse(sdate);
        Timer t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    //邮件发送人
                    simpleMailMessage.setFrom(MAIL_SENDER);
                    //邮件接收人
                    simpleMailMessage.setTo("554417780@qq.com");
                    //邮件主题
                    simpleMailMessage.setSubject("起床啦!!!!");
                    //邮件内容
                    simpleMailMessage.setText("现在是"+LocalDateTime.now()+"还不起床!!!");
                    javaMailSender.send(simpleMailMessage);
                } catch (Exception e) {
                    log.error("邮件发送失败", e.getMessage());
                }
            }
        //86400000
        },date,86400000);
    }
}
