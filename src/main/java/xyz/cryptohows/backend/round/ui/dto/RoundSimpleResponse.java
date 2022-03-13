package xyz.cryptohows.backend.round.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.ui.dto.ProjectSimpleResponse;
import xyz.cryptohows.backend.round.domain.Round;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoundSimpleResponse {

    private final Long id;
    private final ProjectSimpleResponse project;
    private final String announcedDate;
    private final String moneyRaised;
    private final String newsArticle;
    private final String fundingStage;
    private final Integer totalParticipants;

    public RoundSimpleResponse(Long id, ProjectSimpleResponse project, String announcedDate, String moneyRaised,
                         String newsArticle, String fundingStage, Integer totalParticipants) {
        this.id = id;
        this.project = project;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.newsArticle = newsArticle;
        this.fundingStage = fundingStage;
        this.totalParticipants = totalParticipants;
    }

    public static RoundSimpleResponse of(Round round) {
        return new RoundSimpleResponse(
                round.getId(),
                ProjectSimpleResponse.of(round.getProject()),
                round.getAnnouncedDate().toString(),
                round.getMoneyRaised(),
                round.getNewsArticle(),
                round.getFundingStage().getFundingStage(),
                round.getTotalParticipants()
        );
    }

    public static List<RoundSimpleResponse> toList(List<Round> rounds) {
        return rounds.stream()
                .map(RoundSimpleResponse::of)
                .collect(Collectors.toList());
    }
}
