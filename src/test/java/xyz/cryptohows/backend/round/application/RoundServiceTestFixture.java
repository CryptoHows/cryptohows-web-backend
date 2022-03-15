package xyz.cryptohows.backend.round.application;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.persistence.EntityManager;
import java.util.Arrays;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class RoundServiceTestFixture {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundParticipationRepository roundParticipationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private EntityManager em;

    protected final VentureCapital hashed = VentureCapital.builder()
            .name("해시드")
            .about("한국의 VC")
            .homepage("hashed.com")
            .logo("hashed.png")
            .build();

    protected final VentureCapital a16z = VentureCapital.builder()
            .name("a16z")
            .about("미국 VC")
            .homepage("a16z.com")
            .logo("a16z.png")
            .build();

    protected final Project LUNA = Project.builder()
            .name("LUNA")
            .about("LUNA 프로젝트")
            .homepage("https://LUNA.io/")
            .category(Category.INFRASTRUCTURE)
            .mainnet(Mainnet.TERRA)
            .build();

    protected final Project SOLANA = Project.builder()
            .name("SOLANA")
            .about("SOLANA 프로젝트")
            .homepage("https://SOLANA.io/")
            .category(Category.INFRASTRUCTURE)
            .mainnet(Mainnet.SOLANA)
            .build();

    protected final Project axieInfinity = Project.builder()
            .name("axieInfinity")
            .about("엑시 인피니티")
            .homepage("https://axieInfinity.xyz/")
            .category(Category.WEB3)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    protected final Round EOSSeed = Round.builder()
            .project(SOLANA)
            .announcedDate("2019-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    protected final Round axieSeed = Round.builder()
            .project(axieInfinity)
            .announcedDate("2019-11")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    protected final Round EOSSeriesA = Round.builder()
            .project(SOLANA)
            .announcedDate("2020-02")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    protected final Round axieSeriesA = Round.builder()
            .project(axieInfinity)
            .announcedDate("2020-03")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    protected final Round lunaSeriesB = Round.builder()
            .project(LUNA)
            .announcedDate("2017-01")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_B)
            .build();

    protected final Round lunaSeriesC = Round.builder()
            .project(LUNA)
            .announcedDate("2018-01")
            .moneyRaised("$200M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_C)
            .build();

    @BeforeEach
    void setUp() {
        projectRepository.saveAll(Arrays.asList(SOLANA, LUNA, axieInfinity));
        ventureCapitalRepository.saveAll(Arrays.asList(hashed, a16z));

        roundRepository.save(EOSSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeed));
        roundParticipationRepository.save(new RoundParticipation(a16z, EOSSeed));

        roundRepository.save(axieSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, axieSeed));
        roundParticipationRepository.save(new RoundParticipation(a16z, axieSeed));

        roundRepository.save(EOSSeriesA);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeriesA));
        roundParticipationRepository.save(new RoundParticipation(a16z, EOSSeriesA));

        roundRepository.save(axieSeriesA);
        roundParticipationRepository.save(new RoundParticipation(hashed, axieSeriesA));
        roundParticipationRepository.save(new RoundParticipation(a16z, axieSeriesA));

        roundRepository.save(lunaSeriesB);
        roundParticipationRepository.save(new RoundParticipation(hashed, lunaSeriesB));
        roundParticipationRepository.save(new RoundParticipation(a16z, lunaSeriesB));

        roundRepository.save(lunaSeriesC);
        roundParticipationRepository.save(new RoundParticipation(hashed, lunaSeriesC));
        roundParticipationRepository.save(new RoundParticipation(a16z, lunaSeriesC));

        em.clear();
        em.flush();
    }
}
