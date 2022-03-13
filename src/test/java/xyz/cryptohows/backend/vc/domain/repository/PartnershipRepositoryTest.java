package xyz.cryptohows.backend.vc.domain.repository;

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
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PartnershipRepositoryTest {

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @Autowired
    private ProjectRepository projectRepository;

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

    @BeforeEach
    void setUp() {
        projectRepository.save(EOS);
        projectRepository.save(axieInfinity);
        ventureCapitalRepository.save(hashed);
    }

    @Test
    @DisplayName("Partnership을 저장하면 VentureCapital과 Project에서 이를 조회할 수 있다")
    void checkPartnership() {
        // given
        Partnership hashedEOS = new Partnership(hashed, EOS);
        Partnership hashedAxieInfinity = new Partnership(hashed, axieInfinity);
        partnershipRepository.saveAll(Arrays.asList(hashedEOS, hashedAxieInfinity));
        tem.flush();
        tem.clear();

        // when
        VentureCapital savedHashed = ventureCapitalRepository.findById(hashed.getId())
                .orElseThrow(IllegalArgumentException::new);
        Project savedEOS = projectRepository.findById(EOS.getId())
                .orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(savedHashed.getPartnerships()).hasSize(2);
        assertThat(savedEOS.getPartnerships()).hasSize(1);
    }

    @Test
    @DisplayName("VentureCapital의 Partnership을 삭제할 수 있다.")
    void deleteByVC() {
        // given
        Partnership hashedEOS = new Partnership(hashed, EOS);
        Partnership hashedAxieInfinity = new Partnership(hashed, axieInfinity);
        partnershipRepository.saveAll(Arrays.asList(hashedEOS, hashedAxieInfinity));
        tem.flush();
        tem.clear();

        // when
        partnershipRepository.deleteByVentureCapital(hashed);

        // when
        assertThat(partnershipRepository.count()).isZero();
    }
}