package org.arfath.springai.controllers;

import org.arfath.springai.services.chatservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class chatController {
    public final chatservice chatservice;


    public chatController(chatservice chatservice) {
        this.chatservice = chatservice;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestParam(value = "q", required = true) String q) {

        return ResponseEntity.ok(chatservice.getResponse(q));

    }


}