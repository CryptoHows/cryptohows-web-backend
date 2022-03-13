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
    private final String twitter;
    private final String community;
    private final String round;
    private final String category;

    public ProjectSimpleResponse(Long id, String name, String about, String homepage, String logo, String twitter,
                                 String community, String round, String category) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.twitter = twitter;
        this.community = community;
        this.round = round;
        this.category = category;
    }

    public static ProjectSimpleResponse of(Project project) {
        return new ProjectSimpleResponse(
                project.getId(),
                project.getName(),
                project.getAbout(),
                project.getHomepage(),
                project.getLogo(),
                project.getTwitter(),
                project.getCommunity(),
                project.getCurrentRound().getFundingStage(),
                project.getCategory().getCategoryName()
        );
    }

    public static List<ProjectSimpleResponse> toList(List<Project> portfolio) {
        return portfolio.stream()
                .map(ProjectSimpleResponse::of)
                .collect(Collectors.toList());
    }
}
