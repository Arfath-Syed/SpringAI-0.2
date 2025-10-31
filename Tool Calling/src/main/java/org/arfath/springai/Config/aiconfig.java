package org.arfath.springai.Config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class aiconfig {


    @Bean
    public RestClient restClient(){
        return RestClient
                .builder()
                .baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services")
                .build();
    }


    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){


        return builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }


}
