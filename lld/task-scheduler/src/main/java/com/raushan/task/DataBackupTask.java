package com.raushan.task;

public class DataBackupTask implements Task {
    private final String source;
    private final String destination;

    public DataBackupTask(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void execute() {
        System.out.println("Performing data backup...");
        // Simulate data backup logic here
        try {
            System.out.println("Source: " + source + ", Destination: " + destination);
            Thread.sleep(2000); // Simulate time taken for backup
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Data backup interrupted.");
        }
        System.out.println("Data backup completed.");
    }
}
