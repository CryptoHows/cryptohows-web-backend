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
    private final String mainnet;
    private final Boolean coinAvailable;
    private final List<CoinResponse> coins;

    public ProjectSimpleResponse(Long id, String name, String about, String homepage, String logo, String twitter,
                                 String community, String round, String category, String mainnet, Boolean coinAvailable,
                                 List<CoinResponse> coins) {
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
        this.coinAvailable = coinAvailable;
        this.coins = coins;
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
                project.getCategory().getCategoryName(),
                project.getMainnet().toString(),
                project.hasCoin(),
                CoinResponse.toList(project.getCoins())
        );
    }

    public static List<ProjectSimpleResponse> toList(List<Project> portfolio) {
        return portfolio.stream()
                .map(ProjectSimpleResponse::of)
                .collect(Collectors.toList());
    }
}
