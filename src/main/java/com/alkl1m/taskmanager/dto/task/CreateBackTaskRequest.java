package com.alkl1m.taskmanager.dto.task;

import com.alkl1m.taskmanager.enums.Status;

import java.time.Instant;

public record CreateBackTaskRequest(
    Long id,
    String name,
    String description,
    Instant creationDate,
    Instant completionDate,
    Status status,
    String[] tags) {
        public static CreateBackTaskRequest from(TaskDto taskDto) {
            return new CreateBackTaskRequest(taskDto.id(),
                    taskDto.name(),
                    taskDto.description(),
                    taskDto.creationDate(),
                    taskDto.completionDate(),
                    taskDto.status(),
                    taskDto.tags().split("&#/!&")
            );
        }
}
