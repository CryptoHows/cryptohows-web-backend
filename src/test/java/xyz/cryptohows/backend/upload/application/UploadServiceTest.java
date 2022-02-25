package xyz.cryptohows.backend.upload.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UploadServiceTest {

    @Autowired
    private UploadService uploadService;

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

    @DisplayName("벤처 캐피탈 정보를 담은 엑셀 파일을 업로드 할 수 있다.")
    @Order(1)
    @Test
    void uploadVentureCapitals() {
        // given
        MultipartFile ventureCapitalsFile = ExcelFileFixture.getVentureCapitalsFile();

        // when
        uploadService.uploadVentureCapitals(ventureCapitalsFile);

        // then
        List<VentureCapital> ventureCapitals = ventureCapitalRepository.findAll();
        assertThat(ventureCapitals).hasSize(3);
        assertThat(ventureCapitals.get(0).getName()).isEqualTo("Crypto.com Capital");
        assertThat(ventureCapitals.get(1).getName()).isEqualTo("Spartan Group");
        assertThat(ventureCapitals.get(2).getName()).isEqualTo("GuildFi");
    }

    @DisplayName("프로젝트 정보를 담은 엑셀 파일을 업로드 할 수 있다.")
    @Order(2)
    @Test
    void uploadProjects() {
    }

    @DisplayName("라운드 정보를 담은 엑셀 파일을 업로드 할 수 있다.")
    @Order(3)
    @Test
    void uploadRounds() {
    }
}
