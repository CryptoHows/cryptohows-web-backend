package xyz.cryptohows.backend.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.upload.ProjectUploadService;
import xyz.cryptohows.backend.admin.application.upload.RoundUploadService;
import xyz.cryptohows.backend.admin.application.upload.VentureCapitalUploadService;
import xyz.cryptohows.backend.admin.ui.dto.VentureCapitalRequest;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.Projects;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class VentureCapitalAdminService {

    private final VentureCapitalUploadService ventureCapitalUploadService;
    private final ProjectUploadService projectUploadService;
    private final RoundUploadService roundUploadService;

    private final VentureCapitalRepository ventureCapitalRepository;
    private final ProjectRepository projectRepository;
    private final RoundRepository roundRepository;

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
        deleteProjectOnlyHasOnlyPartner(ventureCapital);
        deleteRoundOnlyHasOnlyParticipant(ventureCapital);
        ventureCapitalRepository.deleteById(vcId);
    }

    private void deleteProjectOnlyHasOnlyPartner(VentureCapital ventureCapital) {
        List<Project> projects = ventureCapital.getPortfolio()
                .stream()
                .filter(project -> project.getNumberOfPartnerships() == 1)
                .collect(Collectors.toList());
        projectRepository.deleteAll(projects);
    }

    private void deleteRoundOnlyHasOnlyParticipant(VentureCapital ventureCapital) {
        List<Round> rounds = ventureCapital.getParticipatedRounds()
                .stream()
                .filter(round -> round.getNumberOfVcParticipants() == 1)
                .collect(Collectors.toList());
        roundRepository.deleteAll(rounds);
    }

    public void create(VentureCapitalRequest ventureCapitalRequest) {
        VentureCapital ventureCapital = VentureCapital.builder()
                .name(ventureCapitalRequest.getName())
                .about(ventureCapitalRequest.getAbout())
                .homepage(ventureCapitalRequest.getHomepage())
                .logo(ventureCapitalRequest.getLogo())
                .build();
        ventureCapitalUploadService.checkExistenceAndSave(ventureCapital);
    }

    public void updateById(Long vcId, VentureCapitalRequest ventureCapitalRequest) {
        VentureCapital ventureCapital = ventureCapitalRepository.findById(vcId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 벤처캐피탈은 없습니다."));
        ventureCapital.updateInformation(
                ventureCapitalRequest.getName(),
                ventureCapitalRequest.getAbout(),
                ventureCapitalRequest.getHomepage(),
                ventureCapitalRequest.getLogo()
        );
    }

    public void uploadNewListingVentureCapital(VentureCapitalRequest ventureCapitalRequest, MultipartFile projects, MultipartFile rounds) {
        create(ventureCapitalRequest);
        projectUploadService.uploadNewListingVentureCapitalProjects(ventureCapitalRequest.getName(), projects);
        roundUploadService.uploadNewListingVentureCapitalRounds(ventureCapitalRequest.getName(), rounds);
    }
}
