package xyz.cryptohows.backend.project.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.cryptohows.backend.project.application.ProjectService;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.ui.dto.ProjectDetailResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectPageResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectSearchResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<ProjectPageResponse> findProjects(@RequestParam(defaultValue = "") String mainnet,
                                                            @RequestParam(defaultValue = "") String category,
                                                            @RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer projectsPerPage) {
        ProjectPageResponse projectPageResponse = projectService.findProjects(mainnet, category, page, projectsPerPage);
        return ResponseEntity.ok(projectPageResponse);
    }

    @GetMapping("/projects/{projectId:[\\d]+}")
    public ResponseEntity<ProjectDetailResponse> findProject(@PathVariable Long projectId) {
        ProjectDetailResponse projectDetailResponse = projectService.findById(projectId);
        return ResponseEntity.ok(projectDetailResponse);
    }

    @GetMapping("/projects/search")
    public ResponseEntity<List<ProjectSearchResponse>> searchProjectContains(@RequestParam("auto_complete") String searchWord) {
        List<ProjectSearchResponse> projectSearchResponses = projectService.searchProjectContains(searchWord);
        return ResponseEntity.ok(projectSearchResponses);
    }

    @GetMapping("/projects/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = projectService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/projects/mainnets")
    public ResponseEntity<List<String>> getAllMainnets() {
        List<String> mainnets = projectService.getAllMainnets();
        return ResponseEntity.ok(mainnets);
    }
}
