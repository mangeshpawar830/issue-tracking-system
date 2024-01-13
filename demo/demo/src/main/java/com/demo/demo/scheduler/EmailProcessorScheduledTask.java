package com.demo.demo.scheduler;


import com.demo.demo.model.service.EmailProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

public class EmailProcessorScheduledTask {

    @Autowired
    private EmailProcessorService emailProcessorService;


    @Scheduled(fixedRate = 60000)
    public void processEmails() {
        emailProcessorService.processEmails();
    }
}
