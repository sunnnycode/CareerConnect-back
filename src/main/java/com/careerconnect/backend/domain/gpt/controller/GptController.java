package com.careerconnect.backend.domain.gpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/gpt")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class GptController {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @GetMapping("/api-key")
    public ResponseEntity<?> getApiKey() {
        return ResponseEntity.ok().body(new ApiKeyResponse(openAiApiKey));
    }

    private static class ApiKeyResponse {
        public String key;

        public ApiKeyResponse(String key) {
            this.key = key;
        }
    }
}
