package com.alkl1m.taskmanager.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateTaskRequest(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Description is required")
        String description) {
}