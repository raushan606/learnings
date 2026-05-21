package com.raushan.observer;

import com.raushan.ScheduledTask;

import java.lang.reflect.Executable;

public interface TaskExecutionObserver {
    void onTaskStarted(ScheduledTask task);
    void onTaskCompleted(ScheduledTask task);
    void onTaskFailed(ScheduledTask task, Exception e);
}
