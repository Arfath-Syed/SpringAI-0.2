package org.arfath.springai.controllers;

import org.arfath.springai.entity.tut;
import org.arfath.springai.services.chatservice;
import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping
public class chatcontroller {
    public final chatservice chatservice;


    public chatcontroller(chatservice chatservice) {
        this.chatservice = chatservice;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestParam(value = "q", required = true) String q) {

        return chatservice.chat(q);

    }
    @GetMapping("/chat1")
    public String chatTemplate(
            @RequestParam(value = "q", required = true) String q,
            @RequestHeader("userId") String userId
            ) {

    return chatservice.chatTemplate(q, userId);

    }


    @GetMapping("/stream-chat")
    public ResponseEntity<Flux<String>> streamChat(
            @RequestParam(value = "q") String q ) {
        return ResponseEntity.ok(chatservice.streamChat(q));


    }

    @GetMapping("/ragchat")
    public String ragChat(
            @RequestParam(value = "q", required = true) String q
    ) {

        return chatservice.ragChat(q);

    }


}