package com.fitness.aiService.service;

import com.fitness.aiService.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group" )
    public void processActivity(Activity activity){
        log.info("Recieved activity for processing {}", activity.getUserId());
    }
}
