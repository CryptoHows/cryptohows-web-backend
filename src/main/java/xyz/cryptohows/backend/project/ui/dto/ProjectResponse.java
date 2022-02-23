package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectResponse {

    private final Long id;
    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;
    private final String round;
    private final List<VentureCapitalSimpleResponse> investors;

    public ProjectResponse(Long id, String name, String about, String homepage, String logo, String round,
                           List<VentureCapitalSimpleResponse> investors) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.round = round;
        this.investors = investors;
    }

    public static ProjectResponse of(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getAbout(),
                project.getHomepage(),
                project.getLogo(),
                project.getCurrentRound().getFundingStage(),
                VentureCapitalSimpleResponse.toList(project.getInvestors())
        );
    }

    public static List<ProjectResponse> toList(List<Project> projects) {
        return projects.stream()
                .map(ProjectResponse::of)
                .collect(Collectors.toList());
    }
}
