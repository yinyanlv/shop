package fun.quzhi.shop.service.impl;

import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.service.EmailService;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * EmailService实现类
 */
@Service
@Configuration
public class EmailServiceImpl implements EmailService {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Constant constant;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage =  new SimpleMailMessage();
        simpleMailMessage.setFrom(Constant.EMAIL_FROM);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        mailSender.send(simpleMailMessage);
    }

    @Override
    public Boolean saveEmailToRedis(String email, String verifyCode) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setPassword(redisPassword);
        RedissonClient client = Redisson.create(config);
        RBucket<String> bucket = client.getBucket(email);
        boolean isExists = bucket.isExists();
        if (!isExists) {
            bucket.set(verifyCode, 60, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

}
