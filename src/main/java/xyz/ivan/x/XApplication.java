package xyz.ivan.x;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("xyz.ivan.x.mapper")
public class XApplication {
    public static void main(String[] args) {
        SpringApplication.run(XApplication.class,args);
    }
}
