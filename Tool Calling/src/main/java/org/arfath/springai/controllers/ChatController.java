package org.arfath.springai.controllers;

import org.arfath.springai.services.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }


    @GetMapping("/chat")
    public String chat(@RequestParam String q){
        return chatService.chat(q);
    }

}
