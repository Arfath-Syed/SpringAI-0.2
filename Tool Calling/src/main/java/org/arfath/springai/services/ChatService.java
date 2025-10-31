package org.arfath.springai.services;

import org.arfath.springai.tools.SimpleDateTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatService {

    private ChatClient chatClient;

    public ChatService(ChatClient chatClient){
        this.chatClient = chatClient;

    }


// information tool

    public String chat(String q){
        return chatClient
                .prompt()
                .tools(new SimpleDateTool())
                .user(q)
                .call()
                .content();
    }

    //Action Tool


}
