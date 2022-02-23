package xyz.cryptohows.backend.round.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Project project;

    private String announcedDate;
    private String moneyRaised;
    private String newsArticle;

    @Enumerated(EnumType.STRING)
    private FundingStage fundingStage;

    @OneToMany(mappedBy = "round", cascade = CascadeType.REMOVE)
    private Set<RoundParticipation> participants = new HashSet<>();

    @Builder
    public Round(Long id, String announcedDate, String moneyRaised, String newsArticle, FundingStage fundingStage) {
        this.id = id;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.newsArticle = newsArticle;
        this.fundingStage = fundingStage;
    }

    public void setProject(Project project) {
        this.project = project;
        project.addRound(this);
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

    public boolean isSameProject(Project project) {
        return this.project.equals(project);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id.equals(round.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
