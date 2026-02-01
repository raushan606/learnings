package com.raushan.strategy;

import com.raushan.data.LogMessage;

public interface LogFormatter {
    String format(LogMessage logMessage);
}
