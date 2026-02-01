package com.raushan.core;

import com.raushan.data.LogMessage;
import com.raushan.strategy.LogAppender;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncLogProcessor {
    private final ExecutorService executor;

    public AsyncLogProcessor() {
        this.executor = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "Async-Log-Processor");
            thread.setDaemon(true);
            return thread;
        });
    }


    public void process(LogMessage logMessage, List<LogAppender> appenders) {
        if (executor.isShutdown()) {
            throw new IllegalStateException("Log processor is shut down");
        }

        executor.submit(() -> {
            for (LogAppender appender : appenders) {
                appender.append(logMessage);
            }
        });
    }

    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                System.err.println("Log processor termination timed out");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
