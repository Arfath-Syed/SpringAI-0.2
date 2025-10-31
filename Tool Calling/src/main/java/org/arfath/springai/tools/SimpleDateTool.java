package org.arfath.springai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Description;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleDateTool {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Tool(description = "get the current date and time in the user zone")
    public String getCurrentDateAndTime(){
        return LocalDateTime.now()
                .atZone(LocaleContextHolder
                        .getTimeZone()
                        .toZoneId())
                .toString();
    }
    @Tool(description = " set alarm for the given time")
    public void setAlarm(@ToolParam(description = "Time in IS)-8601 format") String time){
        var dateTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        this.logger.info("Set the alarm for given time. {}", dateTime);
    }

}
