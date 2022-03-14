package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectResponse {

    private final Long id;
    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;
    private final String twitter;
    private final String community;
    private final String round;
    private final String category;
    private final String mainnet;
    private final List<VentureCapitalSimpleResponse> investors;

    public ProjectResponse(Long id, String name, String about, String homepage, String logo, String twitter, String community,
                           String round, String category, String mainnet, List<VentureCapitalSimpleResponse> investors) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.twitter = twitter;
        this.community = community;
        this.round = round;
        this.category = category;
        this.mainnet = mainnet;
        this.investors = investors;
    }

    public static ProjectResponse of(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getAbout(),
                project.getHomepage(),
                project.getLogo(),
                project.getTwitter(),
                project.getCommunity(),
                project.getCurrentRound().getFundingStage(),
                project.getCategory().getCategoryName(),
                project.getMainnet().toString(),
                VentureCapitalSimpleResponse.toList(project.getInvestors())
        );
    }

    public static List<ProjectResponse> toList(Collection<Project> projects) {
        return projects.stream()
                .map(ProjectResponse::of)
                .collect(Collectors.toList());
    }
}
