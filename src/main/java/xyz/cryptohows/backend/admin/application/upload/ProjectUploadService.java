package xyz.cryptohows.backend.admin.application.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.admin.application.upload.excel.ProjectExcelFormat;
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
public class ProjectUploadService {

    private final VentureCapitalRepository ventureCapitalRepository;
    private final PartnershipRepository partnershipRepository;
    private final ProjectRepository projectRepository;

    public void uploadProjects(MultipartFile file) {
        List<ProjectExcelFormat> projectExcelFormats = ProjectExcelFormat.toList(file);
        List<Project> projects = projectExcelFormats.stream()
                .map(ProjectExcelFormat::toProject)
                .collect(Collectors.toList());
        for (Project project : projects) {
            checkExistenceAndUpload(project);
        }
        uploadPartnerships(projectExcelFormats);
    }

    public void checkExistenceAndUpload(Project project) {
        if (projectRepository.existsByName(project.getName())) {
            throw new CryptoHowsException(project.getName() + "은 이미 업로드 되었거나, 파일 내 중복되어있는 프로젝트입니다.");
        }
        projectRepository.save(project);
    }

    private void uploadPartnerships(List<ProjectExcelFormat> projectExcelFormats) {
        projectExcelFormats.forEach(projectExcelFormat -> {
            Project project = projectRepository.findByNameIgnoreCase(projectExcelFormat.getName());
            savePartnerships(project, projectExcelFormat.getInvestors());
        });
    }

    public void savePartnerships(Project project, List<String> investors) {
        List<VentureCapital> ventureCapitals = ventureCapitalRepository.findAllByNameInIgnoreCase(investors);
        List<Partnership> projectPartnerships = ventureCapitals.stream()
                .map(ventureCapital -> new Partnership(ventureCapital, project))
                .collect(Collectors.toList());
        partnershipRepository.saveAll(projectPartnerships);
    }
}
