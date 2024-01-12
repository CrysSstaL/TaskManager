package com.alkl1m.taskmanager.dto;

import com.alkl1m.taskmanager.entity.Project;
import com.alkl1m.taskmanager.entity.Task;
import com.alkl1m.taskmanager.enums.Status;
import lombok.Data;

import java.time.Instant;

public record TaskDto(Long id,
                      String name,
                      String description,
                      Instant creationDate,
                      Instant completionDate,
                      Status status) {
    public static TaskDto from(Task task) {
        return new TaskDto(task.getId(),
                task.getName(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getDoneAt(),
                task.getStatus()
        );
    }
}
