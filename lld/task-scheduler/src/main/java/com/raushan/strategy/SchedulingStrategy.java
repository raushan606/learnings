package com.raushan.strategy;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SchedulingStrategy {
    Optional<LocalDateTime> nextExecutionTime(LocalDateTime lastExecutionTime);
}
