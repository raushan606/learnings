package com.raushan.strategy;

import com.raushan.data.LogMessage;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements LogAppender {

    private FileWriter writer;
    private LogFormatter formatter;

    public FileAppender(String filePath) throws Exception {
        this.writer = new FileWriter(filePath, true);
        this.formatter = new SimpleTextFormatter();
    }

    @Override
    public synchronized void append(LogMessage logMessage) {
        try {
            writer.write(formatter.format(logMessage));
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failed to write log message to file: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LogFormatter getFormatter() {
        return formatter;
    }

    @Override
    public void setFormatter(LogFormatter formatter) {
        this.formatter = formatter;
    }
}
