package com.example.ubergo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    public Log() {
        // Default constructor
    }
    public Log(LocalDateTime time, String sourceModule, String level, String logContent) {
        this.time = time;
        this.sourceModule = sourceModule;
        this.level = level;
        this.logContent = logContent;
    }

    private LocalDateTime time;
    private String sourceModule;
    private String level;
    private String logContent;
    public Log(String sourceModule, String level, String logContent) {
        this.sourceModule = sourceModule;
        this.level = level;
        this.logContent = logContent;
    }
}
