package com.yaocode.sts.file.web;

import com.yaocode.sts.common.web.annotation.EnableRestApiElapsed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableRestApiElapsed
@SpringBootApplication
@ComponentScan(basePackages = "com.yaocode.sts.file")
public class FileServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
        logger.info("STS File Application Startup Completed");
    }

}
