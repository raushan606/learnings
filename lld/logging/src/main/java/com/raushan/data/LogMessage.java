package com.raushan.data;

import com.raushan.enums.LogLevel;

import java.time.LocalDateTime;

public record LogMessage(LocalDateTime timestamp, LogLevel level, String loggerName, String threadName, String message) {
    public LogMessage(LogLevel level, String loggerName, String message) {
        this(LocalDateTime.now(), level, loggerName, Thread.currentThread().getName(), message);
    }
}
