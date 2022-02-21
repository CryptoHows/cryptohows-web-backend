package xyz.cryptohows.backend.round.domain;

import lombok.Getter;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

@Getter
public class RoundParticipation {

    private final VentureCapital ventureCapital;
    private final Round round;

    public RoundParticipation(VentureCapital ventureCapital, Round round) {
        this.ventureCapital = ventureCapital;
        this.round = round;
    }
}
