package com.xaviervinicius.labschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LabScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabScheduleApplication.class, args);
    }

}
