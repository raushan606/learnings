package com.raushan.strategy;

import java.time.LocalDateTime;
import java.util.Optional;

public class OneTimeSchedulingStrategy implements SchedulingStrategy {

    private final LocalDateTime executionTime;

    public OneTimeSchedulingStrategy(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public Optional<LocalDateTime> nextExecutionTime(LocalDateTime lastExecutionTime) {
        return (lastExecutionTime == null) ? Optional.of(executionTime) : Optional.empty();
    }
}
