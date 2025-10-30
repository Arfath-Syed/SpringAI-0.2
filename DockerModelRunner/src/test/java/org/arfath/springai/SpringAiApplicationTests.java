package org.arfath.springai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiApplicationTests {

    @Autowired
    private ChatClient chatClient;

    @Test
    public void testChatClient(){
        System.out.println("testing chat client");

        String query = "what can you do";

        String response = chatClient.prompt()
                .user(query)
                .call()
                .content();
        System.out.println(response);
    }




}
