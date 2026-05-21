package com.raushan;


import com.raushan.observer.LoggingObserver;
import com.raushan.strategy.OneTimeSchedulingStrategy;
import com.raushan.strategy.RecurringSchedulingStrategy;
import com.raushan.strategy.SchedulingStrategy;
import com.raushan.task.DataBackupTask;
import com.raushan.task.PrintMessageTask;
import com.raushan.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public class TaskSchedulerDemo {
    public static void main(String[] args) throws InterruptedException {
        TaskSchedulerService scheduler = TaskSchedulerService.getInstance();
        scheduler.addObserver(new LoggingObserver());
        scheduler.initialize(10);

        // Create a simple task
        Task oneTimeTask = new PrintMessageTask("Hello, this is a one-time task!");
        SchedulingStrategy oneTimeStrategy = new OneTimeSchedulingStrategy(LocalDateTime.now().plus(Duration.ofSeconds(4)));

        Task recurringTask = new PrintMessageTask("Hello, this is a recurring task!");
        SchedulingStrategy recurringStrategy = new RecurringSchedulingStrategy(Duration.ofSeconds(3));

        Task backupTask = new DataBackupTask("/data/source", "/data/backup");
        SchedulingStrategy backupStrategy = new OneTimeSchedulingStrategy(LocalDateTime.now().plus(Duration.ofSeconds(4)));

        System.out.println("Scheduling tasks....");
        scheduler.scheduleTask(oneTimeTask, oneTimeStrategy);
        scheduler.scheduleTask(recurringTask, recurringStrategy);
        scheduler.scheduleTask(backupTask, backupStrategy);

        System.out.println("Waiting for tasks to complete....");
        Thread.sleep(6000);

        // Shutdown the scheduler
        scheduler.shutdown();
    }
}