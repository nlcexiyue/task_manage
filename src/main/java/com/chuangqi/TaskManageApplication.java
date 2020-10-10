package com.chuangqi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

@SpringBootApplication
@MapperScan(value = "com.chuangqi.mapper")
@Import({Swagger2DocumentationConfiguration.class})
@CrossOrigin(origins = "http://192.168.2.177:8000", maxAge = 3600,allowCredentials = "true")
@EnableAsync
@EnableCaching
public class TaskManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManageApplication.class, args);
    }


}
