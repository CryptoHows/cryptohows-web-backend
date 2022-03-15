package xyz.cryptohows.backend.round.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
            .name("해시드")
            .about("한국의 VC")
            .homepage("hashed.com")
            .logo("hashed.png")
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

    private final Round EOSSeed = Round.builder()
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

    private final Round EOSSeriesA = Round.builder()
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

        roundRepository.save(EOSSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeed));

        roundRepository.save(axieSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, axieSeed));

        roundRepository.save(EOSSeriesA);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeriesA));

        roundRepository.save(axieSeriesA);
        roundParticipationRepository.save(new RoundParticipation(hashed, axieSeriesA));

        tem.flush();
        tem.clear();
    }

    @DisplayName("ventureCapital로 roundParticipation을 지울 수 있다.")
    @Test
    void deleteId() {
        // when
        roundParticipationRepository.deleteByVentureCapital(hashed);

        // then
        assertThat(roundParticipationRepository.count()).isZero();
    }
}
