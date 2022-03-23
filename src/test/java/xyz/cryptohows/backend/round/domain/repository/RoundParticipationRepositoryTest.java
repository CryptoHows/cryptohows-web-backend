package xyz.cryptohows.backend.round.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoundParticipationRepositoryTest {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundParticipationRepository roundParticipationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private TestEntityManager tem;

    private final VentureCapital hashed = VentureCapital.builder()
            .name("hashed")
            .about("한국의 VC")
            .homepage("hashed.com")
            .logo("hashed.png")
            .build();

    private final VentureCapital a16z = VentureCapital.builder()
            .name("a16z")
            .about("미국의 VC")
            .homepage("a16z.com")
            .logo("a16z.png")
            .build();

    private final Project SOLANA = Project.builder()
            .name("SOLANA")
            .about("SOLANA 프로젝트")
            .homepage("https://SOLANA.io/")
            .category(Category.INFRASTRUCTURE)
            .mainnet(Mainnet.SOLANA)
            .build();

    private final Project axieInfinity = Project.builder()
            .name("axieInfinity")
            .about("엑시 인피니티")
            .homepage("https://axieInfinity.xyz/")
            .category(Category.WEB3)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    private final Round SolanaSeed = Round.builder()
            .project(SOLANA)
            .announcedDate("2019-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    private final Round axieSeed = Round.builder()
            .project(axieInfinity)
            .announcedDate("2019-11")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    private final Round SolanaSeriesA = Round.builder()
            .project(SOLANA)
            .announcedDate("2020-02")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    private final Round axieSeriesA = Round.builder()
            .project(axieInfinity)
            .announcedDate("2020-03")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    @BeforeEach
    void setUp() {
        projectRepository.saveAll(Arrays.asList(SOLANA, axieInfinity));
        ventureCapitalRepository.save(hashed);
        ventureCapitalRepository.save(a16z);

        roundRepository.save(SolanaSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, SolanaSeed));

        roundRepository.save(axieSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, axieSeed));
        roundParticipationRepository.save(new RoundParticipation(a16z, axieSeed));

        roundRepository.save(SolanaSeriesA);
        roundParticipationRepository.save(new RoundParticipation(hashed, SolanaSeriesA));

        roundRepository.save(axieSeriesA);
        roundParticipationRepository.save(new RoundParticipation(a16z, axieSeriesA));

        tem.flush();
        tem.clear();
    }

    @DisplayName("ventureCapital로 roundParticipation을 지울 수 있다.")
    @Test
    void deleteId() {
        // when
        roundParticipationRepository.deleteByVentureCapital(hashed);

        // then
        assertThat(roundParticipationRepository.count()).isEqualTo(2L);
    }

    @DisplayName("벤처캐피탈이 투자한 라운드가 몇갠지 반환받을 수 있다.")
    @Test
    void countRoundsFilterVentureCapital() {
        // when
        Long count = roundParticipationRepository.countRoundsFilterVentureCapitals(Arrays.asList("hashed"));

        // then
        assertThat(count).isEqualTo(3L);
    }

    @DisplayName("여러개의 벤처캐피탈이 투자한 라운드가 몇갠지 반환받을 수 있다.")
    @Test
    void countRoundsFilterVentureCapitals() {
        // when
        Long count = roundParticipationRepository.countRoundsFilterVentureCapitals(Arrays.asList("hashed", "a16z"));

        // then
        assertThat(count).isEqualTo(4L);
    }

    @DisplayName("여러개의 벤처캐피탈이 투자한 라운드를 반환받을 수 있다.")
    @Test
    void findRoundsFilterVentureCapitals() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Round> rounds = roundParticipationRepository.findRoundsFilterVentureCapitalsOrderByRecentRound(pageable, Arrays.asList("hashed", "a16z"));

        // then
        assertThat(rounds).containsExactly(axieSeriesA, SolanaSeriesA, axieSeed, SolanaSeed);
    }

    @DisplayName("여러개의 벤처캐피탈과 메인넷으로 투자한 라운드가 몇갠지 반환받을 수 있다.")
    @Test
    void countRoundsFilterVentureCapitalsAndMainnet() {
        // when
        Long count = roundParticipationRepository.countRoundsFilterMainnetAndVentureCapitals(Arrays.asList(Mainnet.SOLANA), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("여러개의 벤처캐피탈이 투자한 라운드를 반환받을 수 있다.")
    @Test
    void findRoundsFilterMainnetAndVentureCapitalsOrderByRecentRound() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Round> rounds = roundParticipationRepository.findRoundsFilterMainnetAndVentureCapitalsOrderByRecentRound(pageable, Arrays.asList(Mainnet.SOLANA), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(rounds).containsExactly(SolanaSeriesA, SolanaSeed);
    }
}
