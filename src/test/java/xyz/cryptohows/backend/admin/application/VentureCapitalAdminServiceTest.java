package xyz.cryptohows.backend.admin.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.upload.ExcelFileFixture;
import xyz.cryptohows.backend.admin.application.upload.ProjectUploadService;
import xyz.cryptohows.backend.admin.application.upload.RoundUploadService;
import xyz.cryptohows.backend.admin.application.upload.VentureCapitalUploadService;
import xyz.cryptohows.backend.admin.ui.dto.VentureCapitalRequest;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class VentureCapitalAdminServiceTest {

    @Autowired
    private VentureCapitalAdminService ventureCapitalAdminService;

    @Autowired
    private VentureCapitalUploadService ventureCapitalUploadService;

    @Autowired
    private ProjectUploadService projectUploadService;

    @Autowired
    private RoundUploadService roundUploadService;

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundParticipationRepository roundParticipationRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("새로운 VC를 프로젝트/라운드 정보와 함께 추가할 수 있다.")
    @Test
    void addNewListingVC() {
        // given
        MultipartFile ventureCapitalsFile = ExcelFileFixture.getExistingVentureCapitalsFile();
        ventureCapitalUploadService.uploadVentureCapitals(ventureCapitalsFile);

        MultipartFile projectsFile = ExcelFileFixture.getExistingProjects();
        projectUploadService.uploadProjects(projectsFile);

        MultipartFile roundsFile = ExcelFileFixture.getExistingRounds();
        roundUploadService.uploadRounds(roundsFile);

        // when
        VentureCapitalRequest JoelVC = new VentureCapitalRequest("Joel", "Joel vc", "https://joel-dev.site", "https://joel");
        MultipartFile newVCProjects = ExcelFileFixture.getNewVCProjects();
        MultipartFile newVCRounds = ExcelFileFixture.getNewVCRounds();

        ventureCapitalAdminService.uploadNewListingVentureCapital(JoelVC, newVCProjects, newVCRounds);

        em.flush();
        em.clear();

        // then
        assertThat(ventureCapitalRepository.count()).isEqualTo(4L);
        assertThat(projectRepository.count()).isEqualTo(4L);
        assertThat(roundRepository.count()).isEqualTo(4L);
        assertThat(partnershipRepository.count()).isEqualTo(8L);
        assertThat(roundParticipationRepository.count()).isEqualTo(7L);
        VentureCapital joelVC = ventureCapitalRepository.findByNameIgnoreCase("Joel").get();
        List<Round> participatedRounds = joelVC.getParticipatedRounds();
        assertThat(participatedRounds).hasSize(3);
        List<Project> portfolio = joelVC.getPortfolio();
        assertThat(portfolio).hasSize(3);
    }
}