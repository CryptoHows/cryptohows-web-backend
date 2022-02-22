package xyz.cryptohows.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String about;
    private String homepage;
    private String logo;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Mainnet mainnet;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<Partnership> partnerships = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<Round> rounds = new HashSet<>();

    @Builder
    public Project(String name, String about, String homepage, String logo, Category category, Mainnet mainnet) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.category = category;
        this.mainnet = mainnet;
    }

    public void addPartnership(Partnership partnership) {
        if (!partnership.isSameProject(this)) {
            throw new IllegalArgumentException("해당 프로젝트의 파트너쉽이 아닙니다.");
        }
        partnerships.add(partnership);
    }

    public List<VentureCapital> getInvestors() {
        return partnerships.stream()
                .map(Partnership::getVentureCapital)
                .collect(Collectors.toList());
    }

    public void addRound(Round round) {
        if (!round.isSameProject(this)) {
            throw new IllegalArgumentException("해당 프로젝트의 라운드가 아닙니다.");
        }
        rounds.add(round);
    }

    public FundingStage getCurrentRound() {
        return rounds.stream()
                .map(Round::getFundingStage)
                .max(Comparator.comparing(Enum::ordinal))
                .orElse(FundingStage.NONE);
    }
}
