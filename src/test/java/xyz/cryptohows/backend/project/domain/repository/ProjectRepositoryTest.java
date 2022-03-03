package xyz.cryptohows.backend.project.domain.repository;

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
import java.util.LinkedHashSet;
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

    private final VentureCapital a16z = VentureCapital.builder()
            .name("a16z")
            .about("미국 VC")
            .homepage("a16z.com")
            .logo("a16z.png")
            .build();

    private final VentureCapital kakaoVentures = VentureCapital.builder()
            .name("kakaoVentures")
            .about("카카오 VC")
            .homepage("kakaoVentures.com")
            .logo("kakaoVentures.png")
            .build();

    private final Project EOS = Project.builder()
            .name("EOS")
            .about("EOS 프로젝트")
            .homepage("https://EOS.io/")
            .logo("https://EOS.io/logo.png")
            .category(Category.BLOCKCHAIN_INFRASTRUCTURE)
            .mainnet(Mainnet.EOS)
            .build();

    private final Project ETHEREUM = Project.builder()
            .name("ETHEREUM")
            .about("ETHEREUM 프로젝트")
            .homepage("https://ETHEREUM.io/")
            .logo("https://ETHEREUM.io/logo.png")
            .category(Category.BLOCKCHAIN_INFRASTRUCTURE)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    private final Project KLAYTN = Project.builder()
            .name("KLAYTN")
            .about("KLAYTN 프로젝트")
            .homepage("https://KLAYTN.io/")
            .logo("https://KLAYTN.io/logo.png")
            .category(Category.BLOCKCHAIN_INFRASTRUCTURE)
            .mainnet(Mainnet.KLAYTN)
            .build();

    private final Project axieInfinity = Project.builder()
            .name("axieInfinity")
            .about("엑시 인피니티")
            .homepage("https://axieInfinity.xyz/")
            .category(Category.GAMING)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    private final Round EOSSeed = Round.builder()
            .project(EOS)
            .announcedDate("2019-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    @BeforeEach
    void setUp() {
        projectRepository.saveAll(Arrays.asList(EOS, ETHEREUM, KLAYTN, axieInfinity));
        partnershipRepository.saveAll(Arrays.asList(
                new Partnership(hashed, EOS),
                new Partnership(hashed, ETHEREUM),
                new Partnership(hashed, KLAYTN),
                new Partnership(hashed, axieInfinity)
        ));
        ventureCapitalRepository.saveAll(Arrays.asList(hashed, a16z, kakaoVentures));
        tem.clear();
        tem.flush();
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

    @Test
    @DisplayName("프로젝트가 많은 파트너쉽을 가진 순서대대로 반할 수 있다")
    void findProjectsByNumberOfPartnerships() {
        // given
        Partnership hashedEOS = new Partnership(hashed, EOS);
        Partnership hashedETHEREUM = new Partnership(hashed, ETHEREUM);
        Partnership hashedKLAYTN = new Partnership(hashed, KLAYTN);

        Partnership a16zEOS = new Partnership(a16z, EOS);
        Partnership a16zETHEREUM = new Partnership(a16z, ETHEREUM);

        Partnership kakaoVenturesEOS = new Partnership(kakaoVentures, EOS);

        partnershipRepository.saveAll(Arrays.asList(hashedEOS, hashedETHEREUM, hashedKLAYTN,
                a16zEOS, a16zETHEREUM, kakaoVenturesEOS));

        tem.flush();
        tem.clear();

        // when
        LinkedHashSet<Project> projectsByNumberOfPartnerships = projectRepository.findAllProjectsOrderByNumberOfPartnerships();

        // then
        assertThat(projectsByNumberOfPartnerships).containsExactly(EOS, ETHEREUM, KLAYTN);
    }

    @Test
    @DisplayName("프로젝트를 페이지네이션을 통해 구할 수 있다.")
    void findProjects() {
        // when
        List<Project> firstPageProject =
                projectRepository.findProjectsFetchJoinPartnerships(PageRequest.of(0, 2));
        List<Project> secondPageProject =
                projectRepository.findProjectsFetchJoinPartnerships(PageRequest.of(1, 2));

        // then
        assertThat(firstPageProject).hasSize(2);
        assertThat(firstPageProject.get(0)).isEqualTo(EOS);
        assertThat(firstPageProject.get(1)).isEqualTo(ETHEREUM);

        assertThat(secondPageProject).hasSize(2);
        assertThat(secondPageProject.get(0)).isEqualTo(KLAYTN);
        assertThat(secondPageProject.get(1)).isEqualTo(axieInfinity);
    }
}
