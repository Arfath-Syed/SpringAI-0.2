package org.arfath.springai.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class WeatherTool {

    private RestClient restClient;

    @Value("${app.weather.api-key}")
    private String api_key;

    public WeatherTool(RestClient restClient) {
        this.restClient = restClient;
    }

    @Tool(description = " get weather details of the given city")
    public String getWeatherData(
            @ToolParam(description = "city of which the weather details needs to fetched")
            String city){
        System.out.println("getting weather information of the given city");

        return  restClient
                .get()
                .uri(
                        builder -> builder.path("/timeline/{location}")
                                .queryParam("key", api_key)

                                .build(city)
                )
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String,Object>>() {}).toString();

    }
}
