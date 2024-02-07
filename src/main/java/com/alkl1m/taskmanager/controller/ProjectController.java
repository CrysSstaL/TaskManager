package com.alkl1m.taskmanager.controller;

import com.alkl1m.taskmanager.dto.project.*;
import com.alkl1m.taskmanager.service.auth.UserDetailsImpl;
import com.alkl1m.taskmanager.service.project.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/projects")
    public List<ProjectDto> getAllProjects(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return projectService.getAllProjects(userDetails.getId());
    }

    @PostMapping("/projects")
    ResponseEntity<ProjectDto> create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody @Validated CreateProjectRequest request) {
        CreateProjectCommand cmd = new CreateProjectCommand(
                userDetails.getId(),
                request.name(),
                request.description()
        );
        ProjectDto project = projectService.create(cmd);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/api/projects/{id}")
                .buildAndExpand(project.id()).toUri();
        return ResponseEntity.created(location).body(project);
    }

    @PutMapping("/projects/{id}")
    @PreAuthorize("@accessChecker.isProjectBelongToUser(principal, #id)")
    void update(@PathVariable(name = "id") Long id,
                @RequestBody @Validated UpdateProjectRequest request) {
        UpdateProjectCommand cmd = new UpdateProjectCommand(id, request.name(), request.description());
        projectService.update(cmd);
    }

    @DeleteMapping("/projects/{id}")
    @PreAuthorize("@accessChecker.isProjectBelongToUser(principal, #id)")
    ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/projects/{id}/done")
    @PreAuthorize("@accessChecker.isProjectBelongToUser(principal, #id)")
    void changeStatus(@PathVariable(name = "id") Long id) {
        projectService.changeStatus(id);
    }
}
