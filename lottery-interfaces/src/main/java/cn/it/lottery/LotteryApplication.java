package cn.it.lottery;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName LotteryApplication
 * @Description 启动类
 * @Author Matthiola
 * @Date 2023/9/19 15:05
 * @Version 1.0
 * **/
@SpringBootApplication
@Configurable
@EnableDubbo
public class LotteryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
    }

}
