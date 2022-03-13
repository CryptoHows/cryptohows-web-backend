package xyz.cryptohows.backend.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.upload.VentureCapitalUploadService;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Projects;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class VentureCapitalAdminService {

    private final VentureCapitalUploadService ventureCapitalUploadService;
    private final VentureCapitalRepository ventureCapitalRepository;
    private final RoundParticipationRepository roundParticipationRepository;

    public List<VentureCapitalSimpleResponse> findAll() {
        List<VentureCapital> ventureCapitals = ventureCapitalRepository.findAll();
        return VentureCapitalSimpleResponse.toList(ventureCapitals);
    }

    public VentureCapitalResponse findById(Long vcId) {
        VentureCapital ventureCapital = ventureCapitalRepository.findByIdFetchJoinPartnerships(vcId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 벤처캐피탈은 없습니다."));
        Projects portfolio = new Projects(ventureCapital.getPortfolio());
        return VentureCapitalResponse.of(ventureCapital, portfolio.sortProjectsByCategory());
    }

    public void uploadExcel(MultipartFile file) {
        ventureCapitalUploadService.uploadVentureCapitals(file);
    }

    public void deleteById(Long vcId) {
        VentureCapital ventureCapital = ventureCapitalRepository.findById(vcId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 벤처캐피탈은 없습니다."));
        roundParticipationRepository.deleteByVentureCapital(ventureCapital);
        ventureCapitalRepository.deleteById(vcId);
    }
}
