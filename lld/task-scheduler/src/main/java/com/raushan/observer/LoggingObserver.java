package com.raushan.observer;

import com.raushan.ScheduledTask;

public class LoggingObserver implements TaskExecutionObserver {
    @Override
    public void onTaskStarted(ScheduledTask task) {
        System.out.println("Task Started: " + task);
    }

    @Override
    public void onTaskCompleted(ScheduledTask task) {
        System.out.println("Task Completed: " + task);

    }

    @Override
    public void onTaskFailed(ScheduledTask task, Exception e) {
        System.out.println("Task Failed: " + task + ", Error: " + e.getMessage());
    }
}
