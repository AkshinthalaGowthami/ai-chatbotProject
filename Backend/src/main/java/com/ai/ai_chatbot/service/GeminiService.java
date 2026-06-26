package com.ai.ai_chatbot.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String askGemini(String userMessage) {

        String url = apiUrl + "?key=" + apiKey;

        Map<String, Object> textPart = Map.of("text", userMessage);

        Map<String, Object> content = Map.of(
                "parts", List.of(textPart)
        );
        // we sending request

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(content)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);
// then we sending this
        ResponseEntity<Map> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
//        System.out.println(response.getBody());
        // Extract response safely
        try {
            List<Map> candidates = (List<Map>) response.getBody().get("candidates");
            Map first = (Map) candidates.get(0);
            Map contentMap = (Map) first.get("content");
            List<Map> parts = (List<Map>) contentMap.get("parts");

            return parts.get(0).get("text").toString();

        } catch (Exception e) {
            return "Error parsing Gemini response";
        }
    }
}

