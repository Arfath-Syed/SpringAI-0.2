package org.arfath.springai.Config;

//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
//import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class aiconfig {
//
//    @Bean
//    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository){
//        return MessageWindowChatMemory.builder()
//                .chatMemoryRepository(jdbcChatMemoryRepository)
//                .maxMessages(10)
//                .build();
//
//    }

//    public ChatMemory chatMemory(InMemoryChatMemoryRepository inMemoryChatMemoryRepository){
//        return MessageWindowChatMemory.builder()
//                .chatMemoryRepository(inMemoryChatMemoryRepository)
//                .maxMessages(10)
//                .build();
//    }

    private Logger logger = LoggerFactory.getLogger(aiconfig.class);
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory){

//        this.logger.info("chatMemoryImplementation class " + chatMemory.getClass().getName());
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder
                .defaultAdvisors(messageChatMemoryAdvisor,new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("games")))
                .defaultOptions(
                        OpenAiChatOptions
                                .builder()
                                .model("gpt-5-nano")
                                .temperature(1.0)
                                .build()
                )
                .build();
    }

//    @Bean(name = "openAiChatClient")
//    public ChatClient openAiChatModel(OpenAiChatModel openAiChatModel){
//        return ChatClient.builder(openAiChatModel).build();
//    }
//
//    @Bean(name = "geminiChatClient")
//    public ChatClient geminiChatModel(VertexAiGeminiChatModel geminiChatModel){
//        return ChatClient.builder(geminiChatModel).build();
//    }
}
