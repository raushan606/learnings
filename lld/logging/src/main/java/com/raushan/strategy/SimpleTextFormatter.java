package com.raushan.strategy;

import com.raushan.data.LogMessage;

import java.time.format.DateTimeFormatter;

public class SimpleTextFormatter implements LogFormatter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogMessage logMessage) {
        return String.format("[%s] [%s] [%s] [%s] - %s",
                logMessage.timestamp().format(DATE_TIME_FORMATTER),
                logMessage.level(),
                logMessage.loggerName(),
                logMessage.threadName(),
                logMessage.message());
    }
}
