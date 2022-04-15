package xyz.cryptohows.backend.admin.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import xyz.cryptohows.backend.admin.ui.dto.ProjectRequest;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ProjectAdminServiceTest {

    @Autowired
    private ProjectAdminService projectAdminService;

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @Autowired
    private EntityManager em;

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

    private final Project LUNA = Project.builder()
            .name("LUNA")
            .about("LUNA 프로젝트")
            .homepage("https://LUNA.io/")
            .category(Category.INFRASTRUCTURE)
            .mainnet(Mainnet.TERRA)
            .build();

    private final ProjectRequest JOEL_REQUEST = new ProjectRequest(
            "Joel",
            "Joel 프로젝트",
            "https://joel-dev.site",
            "https://joel.image",
            "https://joel.twitter",
            "https://joel.discord",
            "defi",
            "terra",
            "hashed, a16z"
    );

    @BeforeEach
    void setUp() {
        ventureCapitalRepository.save(hashed);
        ventureCapitalRepository.save(a16z);
        projectRepository.save(LUNA);
        partnershipRepository.save(new Partnership(hashed, LUNA));
    }

    @DisplayName("프로젝트의 정보를 수정할 수 있다.")
    @Test
    void updateById() {
        // given
        Long projectId = LUNA.getId();

        // when
        projectAdminService.updateById(projectId, JOEL_REQUEST);
        em.flush();
        em.clear();

        // then
        assertThat(partnershipRepository.count()).isEqualTo(2L);
        assertThat(projectRepository.findById(projectId).get().getName()).isEqualTo("Joel");
    }
}