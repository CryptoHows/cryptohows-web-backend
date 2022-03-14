package xyz.cryptohows.backend.project.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.project.application.ProjectService;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.ui.dto.ProjectDetailResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectPageResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<ProjectPageResponse> findProjects(@RequestParam Integer page,
                                                            @RequestParam(defaultValue = "10") Integer projectsPerPage) {
        ProjectPageResponse projectPageResponse = projectService.findProjects(page, projectsPerPage);
        return ResponseEntity.ok(projectPageResponse);
    }

    @GetMapping("/projects/{projectId:[\\d]+}")
    public ResponseEntity<ProjectDetailResponse> findProject(@PathVariable Long projectId) {
        ProjectDetailResponse projectDetailResponse = projectService.findById(projectId);
        return ResponseEntity.ok(projectDetailResponse);
    }

    @GetMapping("/projects/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = Category.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/projects/mainnets")
    public ResponseEntity<List<String>> getAllMainnets() {
        List<String> mainnets = Mainnet.getAllMainnets();
        return ResponseEntity.ok(mainnets);
    }
}
