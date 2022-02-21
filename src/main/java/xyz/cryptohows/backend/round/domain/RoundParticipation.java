package xyz.cryptohows.backend.round.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoundParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private VentureCapital ventureCapital;

    @ManyToOne(fetch = FetchType.LAZY)
    private Round round;

    public RoundParticipation(VentureCapital ventureCapital, Round round) {
        this.ventureCapital = ventureCapital;
        this.round = round;
    }
}
