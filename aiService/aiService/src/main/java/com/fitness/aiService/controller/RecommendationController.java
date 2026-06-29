package com.fitness.aiService.controller;

import com.fitness.aiService.model.Recommendations;
import com.fitness.aiService.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ ")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationservice;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendations>> getUserRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(recommendationservice.getUserRecommendation(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendations> getActivityRecommendations(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationservice.getActivityRecommendation(activityId));
    }


}
