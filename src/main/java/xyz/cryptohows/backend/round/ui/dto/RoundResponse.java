package xyz.cryptohows.backend.round.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.ui.dto.ProjectSimpleResponse;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoundResponse {

    private final Long id;
    private final ProjectSimpleResponse project;
    private final String announcedDate;
    private final String moneyRaised;
    private final String newsArticle;
    private final String fundingStage;
    private final List<VentureCapitalSimpleResponse> participants;

    public RoundResponse(Long id, ProjectSimpleResponse project, String announcedDate, String moneyRaised,
                         String newsArticle, String fundingStage, List<VentureCapitalSimpleResponse> participants) {
        this.id = id;
        this.project = project;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.newsArticle = newsArticle;
        this.fundingStage = fundingStage;
        this.participants = participants;
    }

    public static RoundResponse of(Round round) {
        return new RoundResponse(
                round.getId(),
                ProjectSimpleResponse.of(round.getProject()),
                round.getAnnouncedDate().toString(),
                round.getMoneyRaised(),
                round.getNewsArticle(),
                round.getFundingStage().getFundingStage(),
                VentureCapitalSimpleResponse.toList(round.getParticipatedVC())
        );
    }

    public static List<RoundResponse> toList(List<Round> rounds) {
        return rounds.stream()
                .map(RoundResponse::of)
                .collect(Collectors.toList());
    }
}
