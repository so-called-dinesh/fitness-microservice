package com.fitness.aiService.service;

import com.fitness.aiService.model.Activity;
import com.fitness.aiService.model.Recommendations;
import com.fitness.aiService.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    private final ActivityAiService activityAiService;
    private final RecommendationRepo recommendationRepo;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group" )
    public void processActivity(Activity activity){
        log.info("Received activity for processing {}", activity.getUserId());
        Recommendations recommendations = activityAiService.generateRecommendation(activity);
        log.info("AI RESPONSE AFTER PROCESSING: " + recommendations);
        recommendationRepo.save(recommendations);
    }
}
