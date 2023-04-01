package com.example.ubergo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private LocalDateTime time;
    private String sourceModule;
    private String level;
    private String logContent;
}
