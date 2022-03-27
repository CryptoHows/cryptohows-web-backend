package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.List;

@Getter
public class ProjectPageResponse {

    private final Long totalProjects;
    private final List<ProjectResponse> projects;

    public ProjectPageResponse(Long totalProjects, List<ProjectResponse> projects) {
        this.totalProjects = totalProjects;
        this.projects = projects;
    }

    public static ProjectPageResponse of(long totalProjectCount, List<Project> projects) {
        return new ProjectPageResponse(
                totalProjectCount,
                ProjectResponse.toList(projects)
        );
    }
}
