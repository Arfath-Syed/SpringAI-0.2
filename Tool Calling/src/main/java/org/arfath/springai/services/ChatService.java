package org.arfath.springai.services;

import org.arfath.springai.tools.SimpleDateTool;
import org.arfath.springai.tools.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatService {

    private final WeatherTool weatherTool;
    private ChatClient chatClient;

    public ChatService(ChatClient chatClient, WeatherTool weatherTool){
        this.chatClient = chatClient;
        this.weatherTool = weatherTool;
    }


// information tool

    public String chat(String q){
        return chatClient
                .prompt()
                .tools(new SimpleDateTool(),weatherTool)
                .user(q)
                .call()
                .content();
    }

    //Action Tool


}
