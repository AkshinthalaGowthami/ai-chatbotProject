package com.ai.ai_chatbot.controller;

import com.ai.ai_chatbot.dto.ChatRequest;
import com.ai.ai_chatbot.service.GeminiService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final GeminiService geminiService;

    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        return geminiService.askGemini(request.getMessage());
    }
}
