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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VentureCapitalRepositoryTest {

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @Autowired
    private ProjectRepository projectRepository;

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

    @BeforeEach
    void setUp() {
        projectRepository.save(SOLANA);
        projectRepository.save(axieInfinity);
        ventureCapitalRepository.save(hashed);
        ventureCapitalRepository.save(a16z);
        tem.flush();
        tem.clear();
    }

    @DisplayName("VentureCapital의 이름 리스트로 조회할 수 있다.")
    @Test
    void findAllName() {
        // when
        List<VentureCapital> vcs = ventureCapitalRepository.findAllByNameInIgnoreCase(Arrays.asList("hashed", "a16z", "flower"));

        // then
        assertThat(vcs).hasSize(2);
        assertThat(vcs).containsExactly(hashed, a16z);
    }

    @DisplayName("VentureCapital이 없어지면, 해당 회사에서 투자한 Partnership 내역은 사라진다.")
    @Test
    void deleteVentureCapital() {
        // given
        Partnership hashedEOS = new Partnership(hashed, SOLANA);
        Partnership hashedAxieInfinity = new Partnership(hashed, axieInfinity);
        partnershipRepository.saveAll(Arrays.asList(hashedEOS, hashedAxieInfinity));
        tem.flush();
        tem.clear();

        // when
        ventureCapitalRepository.deleteById(hashed.getId());

        // then
        List<Partnership> partnerships = partnershipRepository.findAll();
        assertThat(partnerships).isEmpty();
    }

    @DisplayName("VentureCapital의 이름 리스트로 조회할 수 있으며, 대소문자는 상관이 없다.")
    @Test
    void findAllNameIgnoreCase() {
        // when
        List<VentureCapital> vcs = ventureCapitalRepository.findAllByNameInIgnoreCase(Arrays.asList("hashed", "A16Z", "flower"));

        // then
        assertThat(vcs).hasSize(2);
        assertThat(vcs).containsExactly(hashed, a16z);
    }

    @DisplayName("VentureCapital의 이름을 알파벳 순으로 조회한다.")
    @Test
    void findAllNames() {
        // when
        List<String> vcnames = ventureCapitalRepository.findAllNames();
        // then
        assertThat(vcnames).hasSize(2);
        assertThat(vcnames).containsExactly("a16z", "hashed");
    }
}
