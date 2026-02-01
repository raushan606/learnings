package com.raushan.strategy;

import com.raushan.data.LogMessage;

public interface LogAppender {
    void append(LogMessage logMessage);

    void close();

    LogFormatter getFormatter();

    void setFormatter(LogFormatter formatter);
}
