package com.raushan.strategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class RecurringSchedulingStrategy implements SchedulingStrategy{

    private final Duration duration;

    public RecurringSchedulingStrategy(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Optional<LocalDateTime> nextExecutionTime(LocalDateTime lastExecutionTime) {
        LocalDateTime baseTime = (lastExecutionTime == null) ? LocalDateTime.now() : lastExecutionTime;
        return Optional.of(baseTime.plus(duration));
    }
}
