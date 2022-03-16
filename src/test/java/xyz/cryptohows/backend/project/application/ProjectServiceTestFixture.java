package xyz.cryptohows.backend.project.application;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.persistence.EntityManager;
import java.util.Arrays;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class ProjectServiceTestFixture {

    @Autowired
    private PartnershipRepository partnershipRepository;

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

    @BeforeEach
    void setUp() {
        projectRepository.saveAll(Arrays.asList(SOLANA, LUNA, axieInfinity));
        ventureCapitalRepository.saveAll(Arrays.asList(hashed, a16z));

        partnershipRepository.save(new Partnership(hashed, SOLANA));
        partnershipRepository.save(new Partnership(hashed, LUNA));
        partnershipRepository.save(new Partnership(hashed, axieInfinity));

        partnershipRepository.save(new Partnership(a16z, SOLANA));
        partnershipRepository.save(new Partnership(a16z, LUNA));
        partnershipRepository.save(new Partnership(a16z, axieInfinity));
    }
}
