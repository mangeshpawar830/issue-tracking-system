package com.demo.demo.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class SchedulerConfig {
	
	@Bean
    public EmailProcessorScheduledTask emailProcessorScheduledTask() {
        return new EmailProcessorScheduledTask();
    }

}
