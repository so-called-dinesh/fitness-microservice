package com.fitness.aiService.service;

import com.fitness.aiService.model.Recommendations;
import com.fitness.aiService.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepo recommendationRepo;

    public @Nullable List<Recommendations> getUserRecommendation(String userId) {
        return recommendationRepo.findAllById(userId);
    }

    public Recommendations getActivityRecommendation(String activityId) {
        return recommendationRepo.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No Recommendation found for the activityID : "+ activityId));
    }
}
