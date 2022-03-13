package xyz.cryptohows.backend.admin.application.upload;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UploadServiceTest {

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

    @DisplayName("벤처 캐피탈 정보를 담은 엑셀 파일을 업로드 할 수 있다.")
    @Test
    void uploadVentureCapitals() {
        // given
        MultipartFile ventureCapitalsFile = ExcelFileFixture.getVentureCapitalsFile();

        // when
        ventureCapitalUploadService.uploadVentureCapitals(ventureCapitalsFile);

        // then
        List<VentureCapital> ventureCapitals = ventureCapitalRepository.findAll();
        assertThat(ventureCapitals).hasSize(3);
        assertThat(ventureCapitals.get(0).getName()).isEqualTo("Crypto.com Capital");
        assertThat(ventureCapitals.get(1).getName()).isEqualTo("Spartan Group");
        assertThat(ventureCapitals.get(2).getName()).isEqualTo("GuildFi");
    }

    @DisplayName("프로젝트 정보를 담은 엑셀 파일을 업로드 할 수 있다.")
    @Test
    void uploadProjects() {
        // given
        uploadVentureCapitals();
        MultipartFile projectsFile = ExcelFileFixture.getProjects();

        // when
        projectUploadService.uploadProjects(projectsFile);

        // then
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(3);
        assertThat(projects.get(0).getName()).isEqualTo("Pendle Finance");
        assertThat(projects.get(1).getName()).isEqualTo("Heroes of Mavia");
        assertThat(projects.get(2).getName()).isEqualTo("Cyball");

        List<Partnership> partnerships = partnershipRepository.findAll();
        assertThat(partnerships).hasSize(5);
        assertThat(partnerships.get(0).getProject().getName()).isEqualTo("Pendle Finance");
        assertThat(partnerships.get(0).getVentureCapital().getName()).isEqualTo("Crypto.com Capital");
        assertThat(partnerships.get(1).getProject().getName()).isEqualTo("Pendle Finance");
        assertThat(partnerships.get(1).getVentureCapital().getName()).isEqualTo("Spartan Group");

        assertThat(partnerships.get(2).getProject().getName()).isEqualTo("Heroes of Mavia");
        assertThat(partnerships.get(2).getVentureCapital().getName()).isEqualTo("Crypto.com Capital");
        assertThat(partnerships.get(3).getProject().getName()).isEqualTo("Heroes of Mavia");
        assertThat(partnerships.get(3).getVentureCapital().getName()).isEqualTo("GuildFi");

        assertThat(partnerships.get(4).getProject().getName()).isEqualTo("Cyball");
        assertThat(partnerships.get(4).getVentureCapital().getName()).isEqualTo("GuildFi");
    }

    @DisplayName("라운드 정보를 담은 엑셀 파일을 업로드 할 수 있다.")
    @Test
    void uploadRounds() {
        // given
        uploadProjects();
        MultipartFile roundsFile = ExcelFileFixture.getRounds();

        // when
        roundUploadService.uploadRounds(roundsFile);

        // then
        List<Round> rounds = roundRepository.findAll();
        assertThat(rounds).hasSize(3);
        assertThat(rounds.get(0).getMoneyRaised()).isEqualTo("$3,700,000");
        assertThat(rounds.get(1).getMoneyRaised()).isEqualTo("$2,500,000");
        assertThat(rounds.get(2).getMoneyRaised()).isEqualTo("$1,800,000");

        List<RoundParticipation> roundParticipations = roundParticipationRepository.findAll();
        assertThat(roundParticipations).hasSize(4);
        assertThat(roundParticipations.get(0).getVentureCapital().getName()).isEqualTo("Crypto.com Capital");
        assertThat(roundParticipations.get(0).getRound().getProject().getName()).isEqualTo("Pendle Finance");

        assertThat(roundParticipations.get(1).getVentureCapital().getName()).isEqualTo("Crypto.com Capital");
        assertThat(roundParticipations.get(1).getRound().getProject().getName()).isEqualTo("Heroes of Mavia");

        assertThat(roundParticipations.get(2).getVentureCapital().getName()).isEqualTo("GuildFi");
        assertThat(roundParticipations.get(2).getRound().getProject().getName()).isEqualTo("Heroes of Mavia");

        assertThat(roundParticipations.get(3).getVentureCapital().getName()).isEqualTo("GuildFi");
        assertThat(roundParticipations.get(3).getRound().getProject().getName()).isEqualTo("Cyball");
    }

    @DisplayName("VC 이름이 중복되면 업로드 할 수 없다.")
    @Test
    void duplicateVCUpload() {
        // given
        MultipartFile ventureCapitalsFile = ExcelFileFixture.getVentureCapitalsFile();
        ventureCapitalUploadService.uploadVentureCapitals(ventureCapitalsFile);

        // when & then
        assertThatThrownBy(() -> ventureCapitalUploadService.uploadVentureCapitals(ventureCapitalsFile))
                .isInstanceOf(CryptoHowsException.class)
                .hasMessageContaining("은 이미 업로드 되었거나, 파일 내 중복되어있는 벤처캐피탈 입니다.");
    }

    @DisplayName("VC 이름이 한 파일에 중복되면 업로드 할 수 없다.")
    @Test
    void duplicateVCUploadSingleFile() {
        // given
        MultipartFile ventureCapitalsFile = ExcelFileFixture.getVentureCapitalsDuplicatedFile();

        // when & then
        assertThatThrownBy(() -> ventureCapitalUploadService.uploadVentureCapitals(ventureCapitalsFile))
                .isInstanceOf(CryptoHowsException.class)
                .hasMessageContaining("은 이미 업로드 되었거나, 파일 내 중복되어있는 벤처캐피탈 입니다.");
    }

    @DisplayName("Project 이름이 중복되면 업로드 할 수 없다.")
    @Test
    void duplicateProjectUpload() {
        // given
        uploadVentureCapitals();
        MultipartFile projectsFile = ExcelFileFixture.getProjects();
        projectUploadService.uploadProjects(projectsFile);

        // when & then
        assertThatThrownBy(() -> projectUploadService.uploadProjects(projectsFile))
                .isInstanceOf(CryptoHowsException.class)
                .hasMessageContaining("은 이미 업로드 되었거나, 파일 내 중복되어있는 프로젝트입니다.");
    }

    @DisplayName("Project 이름이 중복되면 업로드 할 수 없다.")
    @Test
    void duplicateProjectUploadSingleFile() {
        // given
        uploadVentureCapitals();
        MultipartFile projectsFile = ExcelFileFixture.getProjectsDuplicatedFile();

        // when & then
        assertThatThrownBy(() -> projectUploadService.uploadProjects(projectsFile))
                .isInstanceOf(CryptoHowsException.class)
                .hasMessageContaining("은 이미 업로드 되었거나, 파일 내 중복되어있는 프로젝트입니다.");
    }

    @DisplayName("Round에 올라갈 프로젝트가 없다면 오류가 발생한다.")
    @Test
    void roundProjectNotPresent() {
        // given
        MultipartFile projectsFile = ExcelFileFixture.getRoundsProjectNotUploadedFile();

        // when & then
        assertThatThrownBy(() -> roundUploadService.uploadRounds(projectsFile))
                .isInstanceOf(CryptoHowsException.class)
                .hasMessageContaining("은 업로드 되지 않은 프로젝트 입니다.");
    }

    @DisplayName("중복된 라운드 정보를 담은 엑셀 파일을 업로드 할 수 없다.")
    @Test
    void cannotUploadDuplicateRound() {
        // given
        uploadProjects();
        MultipartFile roundsFile = ExcelFileFixture.getRounds();
        roundUploadService.uploadRounds(roundsFile);

        em.flush();
        em.clear();

        // when & then
        assertThatThrownBy(() -> roundUploadService.uploadRounds(roundsFile))
                .isInstanceOf(CryptoHowsException.class)
                .hasMessageContaining("이 등록되어 있거나 파일 내 중복되어 있습니다.");
    }

    @DisplayName("중복된 라운드 정보를 담은 엑셀 파일을 업로드 할 수 없다.")
    @Test
    void cannotUploadDuplicateRoundSingleFile() {
        // given
        uploadProjects();
        MultipartFile roundsFile = ExcelFileFixture.getRoundsDuplicated();

        // when & then
        assertThatThrownBy(() -> roundUploadService.uploadRounds(roundsFile))
                .isInstanceOf(CryptoHowsException.class)
                .hasMessageContaining("이 등록되어 있거나 파일 내 중복되어 있습니다.");
    }
}
