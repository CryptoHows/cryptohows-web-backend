package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.round.ui.dto.RoundWithoutProjectResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectDetailResponse {

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
    private final List<VentureCapitalSimpleResponse> investors;
    private final List<RoundWithoutProjectResponse> rounds;

    public ProjectDetailResponse(Long id, String name, String about, String homepage, String logo, String twitter,
                                 String community, String round, String category, String mainnet, Boolean coinAvailable,
                                 List<CoinResponse> coins, List<VentureCapitalSimpleResponse> investors, List<RoundWithoutProjectResponse> rounds) {
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
        this.investors = investors;
        this.rounds = rounds;
    }

    public static ProjectDetailResponse of(Project project) {
        return new ProjectDetailResponse(
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
                CoinResponse.toList(project.getCoins()),
                VentureCapitalSimpleResponse.toList(project.getInvestors()),
                RoundWithoutProjectResponse.toList(project.getRoundAsRecentOrder())
        );
    }

    public static List<ProjectDetailResponse> toList(Collection<Project> projects) {
        return projects.stream()
                .map(ProjectDetailResponse::of)
                .collect(Collectors.toList());
    }
}
