package com.raushan.task;

public class PrintMessageTask implements Task {
    private String message;

    public PrintMessageTask(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Executing Task: " + message);
    }

    @Override
    public String toString() {
        return "PrintMessageTask{" +
                "message='" + message + '\'' +
                '}';
    }
}
