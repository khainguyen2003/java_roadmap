package com.khai.javaspring.springAnnotation;

import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleClass {
    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
