package com.server.moabook.core.config;

import com.server.moabook.core.scheduler.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final BatchService batchService;

    @Scheduled(fixedDelay = 3600000, initialDelay = 5000)
    public void run() {
        batchService.sendMail();
    }
}
