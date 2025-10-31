package org.arfath.springai;

import org.arfath.springai.tools.WeatherTool;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiApplicationTests {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private WeatherTool weatherTool;


    @Test
    void getWeatherTest(){
        var weatherData = weatherTool.getWeatherData("chicago");
        System.out.println(weatherData);
    }




}
