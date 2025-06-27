package com.github.gvidas333.eventsync.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gvidas333.eventsync.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HuggingFaceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.key}")
    private String apiKey;

    public JsonNode analyzeText(String text) {
        HttpEntity<Map<String, String>> requestEntity = buildRequestEntity(text);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            return parseResponse(response);
        } catch (RestClientException e) {
            throw new ApiException("Failed to get sentiment from hugging face api", e);
        }
    }

    private HttpEntity<Map<String, String>> buildRequestEntity(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, String> requestBody = Collections.singletonMap("inputs", text);

        return new HttpEntity<>(requestBody, headers);
    }

    private JsonNode parseResponse(ResponseEntity<String> responseEntity) {
        try {
            return objectMapper.readTree(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            throw new ApiException("Failed to parse Hugging Face API response", e);
        }
    }
}
