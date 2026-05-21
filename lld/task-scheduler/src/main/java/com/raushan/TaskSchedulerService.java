package com.raushan;

import com.raushan.observer.TaskExecutionObserver;
import com.raushan.strategy.SchedulingStrategy;
import com.raushan.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class TaskSchedulerService {

    private static final TaskSchedulerService INSTANCE = new TaskSchedulerService();
    private final PriorityBlockingQueue<ScheduledTask> taskQueue = new PriorityBlockingQueue<>();
    private final List<TaskExecutionObserver> observers = new ArrayList<>();
    private Thread[] workers;
    private volatile boolean running = true;

    private TaskSchedulerService() {
    }

    public static TaskSchedulerService getInstance() {
        return INSTANCE;
    }

    public void initialize(int workerCount) {
        if (workerCount <= 0) throw new IllegalArgumentException("Worker count must be greater than 0");
        workers = new Thread[workerCount];
        startWorkers();
    }

    public void scheduleTask(Task task, SchedulingStrategy strategy) {
        ScheduledTask scheduledTask = new ScheduledTask(task, strategy);
        taskQueue.offer(scheduledTask);
    }

    private void startWorkers() {
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(this::runWorker, "WorkerThread-" + i);
            workers[i].setDaemon(true);
            workers[i].start();
        }
    }

    private void runWorker() {
        while (running) {
            try {
                var task = taskQueue.take();
                System.out.println("Worker " + Thread.currentThread().getName() + " picked task: " + task.getTask().getClass().getSimpleName() + " scheduled for " + task.getNextExecutionTime());
                LocalDateTime now = LocalDateTime.now();
                long waitTime = 0;
                if (task.getNextExecutionTime().isAfter(now)) {
                    waitTime = Duration.between(now, task.getNextExecutionTime()).toMillis();
                }
                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                }
                var head = taskQueue.peek();
                if (head != null && head.compareTo(task) < 0) {
                    taskQueue.put(task);
                    continue;
                }

                execute(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Worker thread exiting: " + Thread.currentThread().getName());
    }

    void execute(ScheduledTask task) {
        observers.forEach(observers -> observers.onTaskStarted(task));
        try {
            task.getTask().execute();
            task.updateLastExecutionTime();
            observers.forEach(observers -> observers.onTaskCompleted(task));
        } catch (Exception e) {
            observers.forEach(observers -> observers.onTaskFailed(task, e));
        } finally {
            task.updateNextExecutionTime();
            if (task.hasMoreExecutions()) {
                taskQueue.offer(task);
            }
        }
    }

    public void shutdown() {
        running = false;
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }

    public void addObserver(TaskExecutionObserver observer) {
        observers.add(observer);
    }
}
