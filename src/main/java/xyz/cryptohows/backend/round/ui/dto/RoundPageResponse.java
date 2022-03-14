package xyz.cryptohows.backend.round.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.round.domain.Round;

import java.util.List;

@Getter
public class RoundPageResponse {

    private final Long totalRounds;
    private final List<RoundResponse> rounds;

    public RoundPageResponse(Long totalRounds, List<RoundResponse> rounds) {
        this.totalRounds = totalRounds;
        this.rounds = rounds;
    }

    public static RoundPageResponse of(long totalRounds, List<Round> rounds) {
        return new RoundPageResponse(
                totalRounds,
                RoundResponse.toList(rounds)
        );
    }
}
