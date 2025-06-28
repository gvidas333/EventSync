package com.github.gvidas333.eventsync.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gvidas333.eventsync.client.HuggingFaceClient;
import com.github.gvidas333.eventsync.exception.ApiException;
import com.github.gvidas333.eventsync.model.Sentiment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SentimentAnalysisServiceTests {

    @Mock
    private HuggingFaceClient huggingFaceClient;

    @InjectMocks
    private SentimentAnalysisService sentimentAnalysisService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_returnPositive_when_clientReturnsPositiveLabel() throws Exception {

        String jsonResponse = """
                [[
                    {"label": "LABEL_2", "score": 0.99},
                    {"label": "LABEL_1", "score": 0.005},
                    {"label": "LABEL_0", "score": 0.005}
                ]]
                """;
        JsonNode mockResponseNode = objectMapper.readTree(jsonResponse);

        when(huggingFaceClient.analyzeText(anyString())).thenReturn(mockResponseNode);

        Sentiment result = sentimentAnalysisService.analyzeSentiment("Best event ever!");

        assertEquals(Sentiment.POSITIVE, result);
    }

    @Test
    void should_returnNegative_when_clientReturnsNegativeLabel() throws Exception {

        String jsonResponse = """
                [[
                    {"label": "LABEL_2", "score": 0.005},
                    {"label": "LABEL_1", "score": 0.005},
                    {"label": "LABEL_0", "score": 0.99}
                ]]
                """;
        JsonNode mockResponseNode = objectMapper.readTree(jsonResponse);

        when(huggingFaceClient.analyzeText(anyString())).thenReturn(mockResponseNode);

        Sentiment result = sentimentAnalysisService.analyzeSentiment("Worst event ever!");

        assertEquals(Sentiment.NEGATIVE, result);
    }

    @Test
    void should_returnNeutral_when_clientReturnsNeutralLabel() throws Exception {

        String jsonResponse = """
                [[
                    {"label": "LABEL_2", "score": 0.005},
                    {"label": "LABEL_1", "score": 0.99},
                    {"label": "LABEL_0", "score": 0.005}
                ]]
                """;
        JsonNode mockResponseNode = objectMapper.readTree(jsonResponse);

        when(huggingFaceClient.analyzeText(anyString())).thenReturn(mockResponseNode);

        Sentiment result = sentimentAnalysisService.analyzeSentiment("I dont have an opinion");

        assertEquals(Sentiment.NEUTRAL, result);
    }

    @Test
    void should_returnNeutral_when_clientThrowsApiException() {

        when(huggingFaceClient.analyzeText(anyString()))
                .thenThrow(new ApiException("API is down", null));

        Sentiment result = sentimentAnalysisService.analyzeSentiment("Some random feedback");

        assertEquals(Sentiment.NEUTRAL, result);
    }
}