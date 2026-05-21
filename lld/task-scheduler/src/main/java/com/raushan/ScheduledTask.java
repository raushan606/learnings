package com.raushan;

import com.raushan.strategy.SchedulingStrategy;
import com.raushan.task.Task;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class ScheduledTask implements Comparable<ScheduledTask> {

    private final String id;
    private final Task task;
    private final SchedulingStrategy schedulingStrategy;
    private LocalDateTime nextExecutionTime;
    private LocalDateTime lastExecutionTime;

    public ScheduledTask(Task task, SchedulingStrategy schedulingStrategy) {
        this.id = UUID.randomUUID().toString();
        this.task = task;
        this.schedulingStrategy = schedulingStrategy;
        updateNextExecutionTime();
    }

    void updateNextExecutionTime() {
        Optional<LocalDateTime> nextTime = schedulingStrategy.nextExecutionTime(lastExecutionTime);
        this.nextExecutionTime = nextTime.orElse(null);
    }

    public void updateLastExecutionTime() {
        this.lastExecutionTime = nextExecutionTime;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return this.nextExecutionTime.compareTo(o.nextExecutionTime);
    }

    public boolean hasMoreExecutions() {
        return nextExecutionTime != null;
    }

    public String getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public SchedulingStrategy getSchedulingStrategy() {
        return schedulingStrategy;
    }

    public LocalDateTime getNextExecutionTime() {
        return nextExecutionTime;
    }

    public LocalDateTime getLastExecutionTime() {
        return lastExecutionTime;
    }
}
