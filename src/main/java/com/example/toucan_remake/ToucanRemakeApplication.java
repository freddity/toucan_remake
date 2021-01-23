package com.example.toucan_remake;

import com.example.toucan_remake.util.UtilJWT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
/*@EntityScan("com.example.toucan.model.entity")
@ContextConfiguration(classes = {AppConfig.class})*/
public class ToucanRemakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToucanRemakeApplication.class, args);
    }

    @Bean
    public UtilJWT jwtUtil() {
        return new UtilJWT();
    }
}
