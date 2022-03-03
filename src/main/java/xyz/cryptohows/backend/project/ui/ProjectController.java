package xyz.cryptohows.backend.project.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.project.application.ProjectService;
import xyz.cryptohows.backend.project.ui.dto.ProjectCountResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> findProjects(@RequestParam Integer page,
                                                              @RequestParam(defaultValue = "10") Integer projectsPerPage) {
        List<ProjectResponse> projectResponses = projectService.findProjects(page, projectsPerPage);
        return ResponseEntity.ok(projectResponses);
    }

    @GetMapping("/projects/investors-in-descending-order")
    public ResponseEntity<List<ProjectResponse>> orderProjectByNumberOfInvestors() {
        List<ProjectResponse> projectResponses = projectService.orderProjectByNumberOfInvestors();
        return ResponseEntity.ok(projectResponses);
    }

    @GetMapping("/projects/count")
    public ResponseEntity<ProjectCountResponse> countProjects() {
        ProjectCountResponse projectCounts = projectService.countProjects();
        return ResponseEntity.ok(projectCounts);
    }
}
