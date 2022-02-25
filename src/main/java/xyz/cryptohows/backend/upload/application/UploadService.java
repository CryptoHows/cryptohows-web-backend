package xyz.cryptohows.backend.upload.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.upload.application.excel.ProjectExcelFormat;
import xyz.cryptohows.backend.upload.application.excel.VentureCapitalExcelFormat;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.transaction.Transactional;
import java.util.List;
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
        for (ProjectExcelFormat projectExcelFormat : projectExcelFormats) {
            String projectName = projectExcelFormat.getName();
            List<String> investors = projectExcelFormat.getInvestors();

            Project project = projectRepository.findByName(projectName);
            List<VentureCapital> ventureCapitals = ventureCapitalRepository.findAllByNameInIgnoreCase(investors);

            List<Partnership> projectPartnerships = ventureCapitals.stream()
                    .map(ventureCapital -> new Partnership(ventureCapital, project))
                    .collect(Collectors.toList());

            partnershipRepository.saveAll(projectPartnerships);
        }
    }

    public void uploadRounds(MultipartFile file) {

    }
}
