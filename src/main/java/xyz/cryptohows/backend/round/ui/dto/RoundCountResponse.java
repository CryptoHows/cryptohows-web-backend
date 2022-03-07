package xyz.cryptohows.backend.round.ui.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundCountResponse {

    private final Long rounds;

    public RoundCountResponse(Long rounds) {
        this.rounds = rounds;
    }
}
