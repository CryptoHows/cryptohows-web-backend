package xyz.cryptohows.backend.vc.domain.repository;

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
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.Arrays;
import java.util.List;

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

    private final VentureCapital hashed = VentureCapital.builder().name("hashed").about("한국의 VC").homepage("hashed.com").logo("hashed.png").build();

    private final VentureCapital a16z = VentureCapital.builder().name("a16z").about("미국의 VC").homepage("a16z.com").logo("a16z.png").build();

    private final Project SOLANA = Project.builder().name("SOLANA").about("SOLANA 프로젝트").homepage("https://SOLANA.io/").category(Category.INFRASTRUCTURE).mainnet(Mainnet.SOLANA).build();

    private final Project KLAYTN = Project.builder().name("KLAYTN").about("KLAYTN 프로젝트").homepage("https://KLAYTN.io/").category(Category.INFRASTRUCTURE).mainnet(Mainnet.KLAYTN).build();

    private final Project axieInfinity = Project.builder().name("axieInfinity").about("엑시 인피니티").homepage("https://axieInfinity.xyz/").category(Category.WEB3).mainnet(Mainnet.ETHEREUM).build();

    @BeforeEach
    void setUp() {
        projectRepository.save(SOLANA);
        projectRepository.save(KLAYTN);
        projectRepository.save(axieInfinity);
        ventureCapitalRepository.save(hashed);
        ventureCapitalRepository.save(a16z);

        Partnership hashedSolana = new Partnership(hashed, SOLANA);
        Partnership hashedAxieInfinity = new Partnership(hashed, axieInfinity);
        partnershipRepository.saveAll(Arrays.asList(hashedSolana, hashedAxieInfinity));

        Partnership a16zKlaytn = new Partnership(a16z, KLAYTN);
        partnershipRepository.save(a16zKlaytn);

        tem.flush();
        tem.clear();
    }

    @DisplayName("Partnership을 저장하면 VentureCapital과 Project에서 이를 조회할 수 있다")
    @Test
    void checkPartnership() {
        // when
        VentureCapital savedHashed = ventureCapitalRepository.findById(hashed.getId()).orElseThrow(IllegalArgumentException::new);
        Project savedEOS = projectRepository.findById(SOLANA.getId()).orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(savedHashed.getPartnerships()).hasSize(2);
        assertThat(savedEOS.getPartnerships()).hasSize(1);
    }

    @DisplayName("VentureCapital의 Partnership을 삭제할 수 있다.")
    @Test
    void deleteByVC() {
        // when
        partnershipRepository.deleteByVentureCapital(hashed);

        // when
        assertThat(partnershipRepository.count()).isOne();
    }

    @DisplayName("ventureCapital 이름으로 어떤 project가 VC와 파트너쉽을 맺었는지 구할 수 있다.")
    @Test
    void countProjectFilterVentureCapitals() {
        // when
        Long count = partnershipRepository.countProjectFilterVentureCapitals(Arrays.asList("hashed"));

        // then
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("ventureCapital 이름으로 어떤 project가 VC와 파트너쉽을 맺었는지 id 역순으로 반환한다.")
    @Test
    void findProjectsFilterVentureCapitalsOrderByIdDesc() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projects = partnershipRepository.findProjectsFilterVentureCapitalsOrderByIdDesc(pageable, Arrays.asList("hashed"));

        // then
        assertThat(projects).containsExactly(axieInfinity, SOLANA);
    }

    @DisplayName("ventureCapital 없는 이름으로 검색시, 0개의 count가 반환된다.")
    @Test
    void countProjectFilterWithNoNameVentureCapitals() {
        // when
        Long count = partnershipRepository.countProjectFilterVentureCapitals(Arrays.asList("no!", "nono!"));

        // then
        assertThat(count).isEqualTo(0L);
    }

    @DisplayName("ventureCapital 없는 이름으로 검색시, 아무것도 반환하지 않는다. ")
    @Test
    void findProjectsFilterVentureCapitalsInvalid() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projects = partnershipRepository.findProjectsFilterVentureCapitalsOrderByIdDesc(pageable, Arrays.asList("nono!, noway!"));

        // then
        assertThat(projects).isEmpty();
    }

    @DisplayName("ventureCapital 여러개의 이름으로 검색시, 연관 맺은 프로젝트의 갯수가 반환된다.")
    @Test
    void countProjectWithVCName() {
        // when
        Long count = partnershipRepository.countProjectFilterVentureCapitals(Arrays.asList("hashed", "a16z"));

        // then
        assertThat(count).isEqualTo(3L);
    }

    @DisplayName("ventureCapital 여러 이름으로 검색시, 해당 VC와 파트너쉽 맺은 프로젝트 반환한다. ")
    @Test
    void findProjectsFilterMulitpleVentureCapitals() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projects = partnershipRepository.findProjectsFilterVentureCapitalsOrderByIdDesc(pageable, Arrays.asList("hashed", "a16z"));

        // then
        assertThat(projects).containsExactly(axieInfinity, KLAYTN, SOLANA);
    }

    @DisplayName("ventureCapital 여러개의 이름과 메인넷으로 검색시, 연관 맺은 프로젝트의 갯수가 반환된다.")
    @Test
    void countProjectWithVCNameAndMainnet() {
        // when
        Long count = partnershipRepository.countProjectFilterMainnetAndVentureCapitals(Arrays.asList(Mainnet.SOLANA, Mainnet.KLAYTN), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("ventureCapital 여러개의 이름과 카테고리로 검색시, 연관 맺은 프로젝트의 갯수가 반환된다.")
    @Test
    void countProjectWithVCNameAndCategory() {
        // when
        Long count = partnershipRepository.countProjectFilterCategoryAndVentureCapitals(Arrays.asList(Category.INFRASTRUCTURE), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("ventureCapital 여러개의 이름과 메인넷과 카테고리로 검색시, 연관 맺은 프로젝트의 갯수가 반환된다.")
    @Test
    void countProjectFilterMainnetAndCategoryAndVentureCapitals() {
        // when
        Long count = partnershipRepository.countProjectFilterMainnetAndCategoryAndVentureCapitals(Arrays.asList(Mainnet.SOLANA), Arrays.asList(Category.INFRASTRUCTURE), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(count).isEqualTo(1L);
    }

    @DisplayName("ventureCapital과 메인넷으로 검색시, 해당 VC와 파트너쉽 맺은 프로젝트 반환한다. ")
    @Test
    void findProjectsFilterMulitpleVentureCapitalAndMainnet() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projects = partnershipRepository.findProjectsFilterMainnetAndVentureCapitalsOrderByIdDesc(pageable, Arrays.asList(Mainnet.SOLANA, Mainnet.KLAYTN), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(projects).containsExactly(KLAYTN, SOLANA);
    }

    @DisplayName("ventureCapital과 카테고리로 검색시, 해당 VC와 파트너쉽 맺은 프로젝트 반환한다. ")
    @Test
    void findProjectsFilterMulitpleVentureCapitalAndCategory() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projects = partnershipRepository.findProjectsFilterCategoryAndVentureCapitalsOrderByIdDesc(pageable, Arrays.asList(Category.INFRASTRUCTURE), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(projects).containsExactly(KLAYTN, SOLANA);
    }

    @DisplayName("ventureCapital과 카테고리로 메인넷으로 검색시, 해당 VC와 파트너쉽 맺은 프로젝트 반환한다. ")
    @Test
    void findProjectsFilterMainnetAndCategoryAndVentureCapitalsOrderByIdDesc() {
        // when
        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projects = partnershipRepository.findProjectsFilterMainnetAndCategoryAndVentureCapitalsOrderByIdDesc(pageable, Arrays.asList(Mainnet.KLAYTN), Arrays.asList(Category.INFRASTRUCTURE), Arrays.asList("hashed", "a16z"));

        // then
        assertThat(projects).containsExactly(KLAYTN);
    }
}
