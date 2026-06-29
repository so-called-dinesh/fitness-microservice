package com.fitness.aiService.repository;

import com.fitness.aiService.model.Recommendations;
import org.jspecify.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepo extends MongoRepository<Recommendations, String> {
    @Nullable List<Recommendations> findAllById(String userId);

    @Nullable
    Optional <Recommendations> findByActivityId(String activityId);
}
