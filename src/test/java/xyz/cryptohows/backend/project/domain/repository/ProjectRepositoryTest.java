package xyz.cryptohows.backend.project.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundParticipationRepository roundParticipationRepository;

    @Autowired
    private TestEntityManager tem;

    private final VentureCapital hashed = VentureCapital.builder()
            .name("해시드")
            .about("한국의 VC")
            .homepage("hashed.com")
            .logo("hashed.png")
            .build();

    private final Project EOS = Project.builder()
            .name("EOS")
            .about("EOS 프로젝트")
            .homepage("https://EOS.io/")
            .category(Category.BLOCKCHAIN_INFRASTRUCTURE)
            .mainnet(Mainnet.EOS)
            .build();

    private final Project axieInfinity = Project.builder()
            .name("axieInfinity")
            .about("엑시 인피니티")
            .homepage("https://axieInfinity.xyz/")
            .category(Category.GAMING)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    private final Round EOSSeed = Round.builder()
            .announcedDate("2019-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    @BeforeEach
    void setUp() {
        projectRepository.saveAll(Arrays.asList(EOS, axieInfinity));
        ventureCapitalRepository.save(hashed);
    }

    @Test
    @DisplayName("해당 Project 삭제되면 VentureCapital에서 체결했던 파트너쉽이 삭제된다.")
    void deleteProjectPartnershipDeleted() {
        // given
        Partnership hashedEOS = new Partnership(hashed, EOS);
        Partnership hashedAxieInfinity = new Partnership(hashed, axieInfinity);
        partnershipRepository.saveAll(Arrays.asList(hashedEOS, hashedAxieInfinity));
        tem.clear();
        tem.flush();

        // when
        projectRepository.deleteById(EOS.getId());

        // then
        List<Partnership> partnerships = partnershipRepository.findAll();
        assertThat(partnerships).hasSize(1);
    }

    @Test
    @DisplayName("해당 Project 삭제되면, 참여했던 Round가 삭제된다.")
    void deleteProjectRoundDeleted() {
        // given
        EOSSeed.setProject(EOS);
        roundRepository.save(EOSSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeed));
        tem.flush();
        tem.clear();

        // when
        projectRepository.deleteById(EOS.getId());

        // then
        assertThat(roundRepository.count()).isZero();
        assertThat(roundParticipationRepository.count()).isZero();
    }
}
