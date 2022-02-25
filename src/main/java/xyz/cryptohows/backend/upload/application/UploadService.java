package xyz.cryptohows.backend.upload.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.upload.application.excel.ProjectExcelFormat;
import xyz.cryptohows.backend.upload.application.excel.RoundExcelFormat;
import xyz.cryptohows.backend.upload.application.excel.VentureCapitalExcelFormat;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public void uploadProjects(MultipartFile file) {
        List<ProjectExcelFormat> projectExcelFormats = ProjectExcelFormat.toList(file);
        List<Project> projects = projectExcelFormats.stream()
                .map(ProjectExcelFormat::toProject)
                .collect(Collectors.toList());
        projectRepository.saveAll(projects);
        uploadPartnerships(projectExcelFormats);
    }

    private void uploadPartnerships(List<ProjectExcelFormat> projectExcelFormats) {
        projectExcelFormats.forEach(projectExcelFormat -> {
            Project project = projectRepository.findByNameIgnoreCase(projectExcelFormat.getName());
            List<VentureCapital> ventureCapitals =
                    ventureCapitalRepository.findAllByNameInIgnoreCase(projectExcelFormat.getInvestors());
            savePartnerships(project, ventureCapitals);
        });
    }

    private void savePartnerships(Project project, List<VentureCapital> ventureCapitals) {
        List<Partnership> projectPartnerships = ventureCapitals.stream()
                .map(ventureCapital -> new Partnership(ventureCapital, project))
                .collect(Collectors.toList());
        partnershipRepository.saveAll(projectPartnerships);
    }

    public void uploadRounds(MultipartFile file) {
        List<RoundExcelFormat> roundExcelFormats = RoundExcelFormat.toList(file);
        Map<Round, List<VentureCapital>> roundParticipants = new LinkedHashMap<>();
        for (RoundExcelFormat roundExcelFormat : roundExcelFormats) {
            Project project = projectRepository.findByNameIgnoreCase(roundExcelFormat.getProjectName());
            Round round = roundExcelFormat.toRound(project);
            roundRepository.save(round);
            List<VentureCapital> participants =
                    ventureCapitalRepository.findAllByNameInIgnoreCase(roundExcelFormat.getParticipants());
            roundParticipants.put(round, participants);
        }
        uploadRoundParticipation(roundParticipants);
    }

    private void uploadRoundParticipation(Map<Round, List<VentureCapital>> roundParticipants) {
        List<RoundParticipation> roundParticipations = new ArrayList<>();
        for (Map.Entry<Round, List<VentureCapital>> roundParticipant : roundParticipants.entrySet()) {
            Round round = roundParticipant.getKey();
            List<VentureCapital> participants = roundParticipant.getValue();
            for (VentureCapital participant : participants) {
                roundParticipations.add(new RoundParticipation(participant, round));
            }
        }
        roundParticipationRepository.saveAll(roundParticipations);
    }
}
