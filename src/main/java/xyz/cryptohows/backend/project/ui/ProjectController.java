package xyz.cryptohows.backend.project.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.project.application.ProjectService;
import xyz.cryptohows.backend.project.ui.dto.ProjectResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> findAllProjects() {
        List<ProjectResponse> projectResponses = projectService.findAllProjects();
        return ResponseEntity.ok(projectResponses);
    }
}
