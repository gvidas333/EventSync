package com.github.gvidas333.eventsync.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.gvidas333.eventsync.client.HuggingFaceClient;
import com.github.gvidas333.eventsync.exception.ApiException;
import com.github.gvidas333.eventsync.model.Sentiment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SentimentAnalysisService {

    private final HuggingFaceClient huggingFaceClient;

    public Sentiment analyzeSentiment(String text) {
        try {
            JsonNode responseNode = huggingFaceClient.analyzeText(text);

            return convertApiResponseToSentiment(responseNode);
        } catch (ApiException e) {
            System.err.println("API call via HuggingFaceClient error: " + e.getMessage());
            return Sentiment.NEUTRAL;
        }
    }

    private Sentiment convertApiResponseToSentiment(JsonNode responseNode) {
        if (responseNode == null || !responseNode.isArray() || responseNode.isEmpty()) {
            return Sentiment.NEUTRAL;
        }

        String topLabel = findTopLabel(responseNode.get(0));

        return mapLabelToSentiment(topLabel);
    }

    private String findTopLabel(JsonNode scoresNode) {
        String topLabel = "";
        double maxScore = -1.0;

        if (scoresNode != null && scoresNode.isArray()) {
            for (JsonNode scoreNode : scoresNode) {
                double score = scoreNode.get("score").asDouble();
                if (score > maxScore) {
                    maxScore = score;
                    topLabel = scoreNode.get("label").asText();
                }
            }
        }
        return topLabel;
    }

    private Sentiment mapLabelToSentiment(String label) {
        return switch (label) {
            case "LABEL_2" -> Sentiment.POSITIVE;
            case "LABEL_0" -> Sentiment.NEGATIVE;
            default -> Sentiment.NEGATIVE;
        };
    }
}
