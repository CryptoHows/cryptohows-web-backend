package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectSearchResponse {

    private final Long id;
    private final String name;
    private final String about;
    private final String logo;

    public ProjectSearchResponse(Long id, String name, String about, String logo) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.logo = logo;
    }

    public static ProjectSearchResponse of(Project project) {
        return new ProjectSearchResponse(
                project.getId(),
                project.getName(),
                project.getAbout(),
                project.getLogo()
        );
    }

    public static List<ProjectSearchResponse> toList(List<Project> projects) {
        return projects.stream()
                .map(ProjectSearchResponse::of)
                .collect(Collectors.toList());
    }
}
