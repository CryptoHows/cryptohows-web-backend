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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class RoundRepositoryTest {

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
            .fundingStage(FundingStage.SEED)
            .build();

    @BeforeEach
    void setUp() {
        projectRepository.saveAll(Arrays.asList(EOS, axieInfinity));
        ventureCapitalRepository.save(hashed);
    }

    @Test
    @DisplayName("Round의 Project가 없다면 저장되지 않는다.")
    void cannotSaveRound() {
        assertThatThrownBy(() -> roundRepository.save(EOSSeed))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("해당 Round에 벤처캐피탈이 참여할 수 있다.")
    void vcJoinsRound() {
        // given
        EOSSeed.setProject(EOS);
        roundRepository.save(EOSSeed);
        RoundParticipation hashedEOSRound = new RoundParticipation(hashed, EOSSeed);

        // when
        roundParticipationRepository.save(hashedEOSRound);
        tem.flush();
        tem.clear();

        // then
        Round round = roundRepository.findById(EOSSeed.getId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(round.getParticipants()).hasSize(1);
    }

    @Test
    @DisplayName("해당 라운드가 삭제되면, RoundParticipant 모두 삭제된다.")
    void roundDeleted() {
        // given
        EOSSeed.setProject(EOS);
        roundRepository.save(EOSSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeed));
        tem.flush();
        tem.clear();

        // when
        roundRepository.deleteById(EOSSeed.getId());

        // then
        assertThat(roundParticipationRepository.count()).isZero();
    }
}
