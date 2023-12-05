package cn.voriya;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.voriya.*.mapper")
public class SellerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SellerApplication.class,args);
    }
}
