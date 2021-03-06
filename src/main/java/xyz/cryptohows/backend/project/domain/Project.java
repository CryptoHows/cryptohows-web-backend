package xyz.cryptohows.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.exception.DomainException;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String about;
    private String homepage;
    private String logo;
    private String twitter;
    private String community;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Mainnet mainnet = Mainnet.NONE;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<Partnership> partnerships = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<Round> rounds = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Coin> coins = new ArrayList<>();

    @Builder
    public Project(Long id, String name, String about, String homepage, String logo, String twitter, String community,
                   Category category, Mainnet mainnet) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.twitter = twitter;
        this.community = community;
        this.category = category;
        this.mainnet = mainnet;
    }

    public void addPartnership(Partnership partnership) {
        if (!partnership.isSameProject(this)) {
            throw new DomainException("해당 프로젝트의 파트너쉽이 아닙니다.");
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
            throw new DomainException("해당 프로젝트의 라운드가 아닙니다.");
        }
        rounds.add(round);
    }

    public FundingStage getCurrentRound() {
        return rounds.stream()
                .map(Round::getFundingStage)
                .max(Comparator.comparing(Enum::ordinal))
                .orElse(FundingStage.UNKNOWN);
    }

    public void updateInformation(String name, String about, String homepage, String logo, String twitter, String community,
                                  String category, String mainnet) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.twitter = twitter;
        this.community = community;
        this.category = Category.of(category);
        this.mainnet = Mainnet.of(mainnet);
    }

    public List<Round> getRoundAsRecentOrder() {
        return rounds.stream()
                .sorted(Comparator.comparing(Round::getAnnouncedDate).reversed())
                .collect(Collectors.toList());
    }

    public int getNumberOfPartnerships() {
        return this.partnerships.size();
    }

    public void addCoin(Coin coin) {
        if (!this.equals(coin.getProject())) {
            throw new DomainException("해당 프로젝트의 코인이 아닙니다.");
        }
        coins.add(coin);
    }

    public boolean hasCoin() {
        return !coins.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id.equals(project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
