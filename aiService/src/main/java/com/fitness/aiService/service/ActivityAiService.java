package com.fitness.aiService.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiService.model.Activity;
import com.fitness.aiService.model.Recommendations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    public Recommendations generateRecommendation(Activity activity){
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getRecommendations(prompt);
        log.info("RESPONSE FROM AI:\n{}", aiResponse);
        return processAiResponse(activity, aiResponse);
    }

    private Recommendations processAiResponse(Activity activity, String aiResponse) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String jsonContent = extractJson(textNode.asText());

            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode  analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();

            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall: ");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace: ");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate: ");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories: ");

            List<String> improvements = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractSafetyInstructions(analysisJson.path("safety"));

            return Recommendations.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().toString())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendations createDefaultRecommendation(Activity activity) {
        return Recommendations.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType().toString())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with you current routine."))
                .suggestions(Collections.singletonList("Consider consulting a fitness coach."))
                .safety(Arrays.asList(
                        "Always warm-up before you workout",
                        "Stay Hydrated",
                        "Listen to your body."
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyInstructions(JsonNode safetyNode) {
        List<String> instructions = new ArrayList<>();
        if(safetyNode.isArray()){
            safetyNode.forEach(item -> instructions.add(item.asText()));
        }
        return instructions.isEmpty() ?
                Collections.singletonList("Follow general safety guidelines") :
                instructions;

    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if(suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%S: %S", workout, description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestions provided") :
                suggestions;

    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if(improvementsNode.isArray()){
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String details = improvement.path("recommendation").asText();
                improvements.add(String.format("%S: %S", area, details));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No specific improvements provided") :
                improvements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()){
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private String extractJson(String text) {
        // Strip markdown code fences like ```json ... ``` and any leading language tag.
        String stripped = text.trim();
        int firstFence = stripped.indexOf("```");
        if (firstFence >= 0) {
            int afterFence = stripped.indexOf('\n', firstFence);
            if (afterFence < 0) {
                stripped = stripped.substring(firstFence + 3);
            } else {
                stripped = stripped.substring(afterFence + 1);
            }
            int lastFence = stripped.lastIndexOf("```");
            if (lastFence >= 0) {
                stripped = stripped.substring(0, lastFence);
            }
        }
        return stripped.trim();
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above and keep it short, easy to understand, readable and simple.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }

}
