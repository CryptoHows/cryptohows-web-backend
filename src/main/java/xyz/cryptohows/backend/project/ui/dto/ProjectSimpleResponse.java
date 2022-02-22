package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectSimpleResponse {

    private final Long id;
    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;
    private final String round;

    public ProjectSimpleResponse(Long id, String name, String about, String homepage, String logo, String round) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.round = round;
    }

    public static ProjectSimpleResponse of(Project project) {
        return new ProjectSimpleResponse(
                project.getId(),
                project.getName(),
                project.getAbout(),
                project.getHomepage(),
                project.getLogo(),
                project.getCurrentRound().getFundingStage()
        );
    }

    public static List<ProjectSimpleResponse> toList(List<Project> portfolio) {
        return portfolio.stream()
                .map(ProjectSimpleResponse::of)
                .collect(Collectors.toList());
    }
}
