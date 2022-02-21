package xyz.cryptohows.backend.round.domain;

import lombok.Builder;
import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Round {

    private final Project project;
    private final String announcedDate;
    private final String moneyRaised;
    private final FundingType fundingType;

    private final List<RoundParticipation> participants = new ArrayList();

    @Builder
    public Round(Project project, String announcedDate, String moneyRaised, FundingType fundingType) {
        this.project = project;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.fundingType = fundingType;
    }

    public void makeParticipation(VentureCapital ventureCapital) {
        RoundParticipation roundParticipation = new RoundParticipation(ventureCapital, this);
        participants.add(roundParticipation);
    }

    public void makeParticipations(List<VentureCapital> ventureCapitals) {
        for (VentureCapital ventureCapital : ventureCapitals) {
            makeParticipation(ventureCapital);
        }
    }

    public List<VentureCapital> getParticipatedVC() {
        return participants.stream()
                .map(RoundParticipation::getVentureCapital)
                .collect(Collectors.toList());
    }
}
