package com.raushan.core;

import com.raushan.data.LogMessage;
import com.raushan.strategy.LogAppender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LogManager {
    private static final LogManager INSTANCE = new LogManager();
    private final Map<String, Logger> loggers = new ConcurrentHashMap<>();
    private final Logger rootLogger;
    private final AsyncLogProcessor asyncLogProcessor;

    private LogManager() {
        this.rootLogger = new Logger("root", null);
        this.loggers.put("root", rootLogger);
        this.asyncLogProcessor = new AsyncLogProcessor();
    }

    public static LogManager getInstance() {
        return INSTANCE;
    }

    public Logger getLogger(String name) {
        return loggers.computeIfAbsent(name, this::createLogger);
    }

    private Logger createLogger(String name) {
        if (name.equals("root")) {
            return rootLogger;
        }
        int lastDot = name.lastIndexOf('.');
        String parentName = (lastDot == -1) ? "root" : name.substring(0, lastDot);
        Logger parent = getLogger(parentName);
        return new Logger(name, parent);
    }

    public Logger getRootLogger() {
        return rootLogger;
    }

    AsyncLogProcessor getAsyncLogProcessor() {
        return asyncLogProcessor;
    }

    public void shutdown() {
        asyncLogProcessor.stop();
        loggers.values().stream()
                .flatMap(logger -> logger.getAppenders().stream())
                .distinct()
                .forEach(LogAppender::close);
        System.out.println("LogManager shutdown complete.");
    }
}
