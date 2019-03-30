package top.yyqdemao.mytime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import top.yyqdemao.mytime.utilMail.Mail;

@SpringBootApplication
public class MytimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MytimeApplication.class, args);
    }

}
