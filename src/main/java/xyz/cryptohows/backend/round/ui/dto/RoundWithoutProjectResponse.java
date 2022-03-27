package xyz.cryptohows.backend.round.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoundWithoutProjectResponse {

    private final Long id;
    private final String announcedDate;
    private final String moneyRaised;
    private final String newsArticle;
    private final String fundingStage;
    private final Integer totalParticipants;
    private final List<VentureCapitalSimpleResponse> participants;

    public RoundWithoutProjectResponse(Long id, String announcedDate, String moneyRaised, String newsArticle,
                                       String fundingStage, Integer totalParticipants, List<VentureCapitalSimpleResponse> participants) {
        this.id = id;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.newsArticle = newsArticle;
        this.fundingStage = fundingStage;
        this.totalParticipants = totalParticipants;
        this.participants = participants;
    }

    public static RoundWithoutProjectResponse of(Round round) {
        return new RoundWithoutProjectResponse(
                round.getId(),
                round.getAnnouncedDate().toString(),
                round.getMoneyRaised(),
                round.getNewsArticle(),
                round.getFundingStage().getFundingStage(),
                round.getTotalParticipants(),
                VentureCapitalSimpleResponse.toList(round.getParticipatedVC())
        );
    }

    public static List<RoundWithoutProjectResponse> toList(List<Round> rounds) {
        return rounds.stream()
                .map(RoundWithoutProjectResponse::of)
                .collect(Collectors.toList());
    }
}
