package xyz.cryptohows.backend.upload.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.upload.application.excel.VentureCapitalExcelFormat;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final VentureCapitalRepository ventureCapitalRepository;
    private final PartnershipRepository partnershipRepository;
    private final ProjectRepository projectRepository;
    private final RoundRepository roundRepository;
    private final RoundParticipationRepository roundParticipationRepository;

    public void uploadVentureCapitals(MultipartFile file) {
        List<VentureCapitalExcelFormat> ventureCapitalExcelFormats = VentureCapitalExcelFormat.toList(file);
        List<VentureCapital> uploadVentureCapitals = ventureCapitalExcelFormats.stream()
                .map(VentureCapitalExcelFormat::toVentureCapital)
                .collect(Collectors.toList());
        ventureCapitalRepository.saveAll(uploadVentureCapitals);
    }

    public void uploadProjects() {

    }

    public void uploadRounds(){

    }
}
