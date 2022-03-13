package xyz.cryptohows.backend.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.upload.ProjectUploadService;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.project.ui.dto.ProjectResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectSimpleResponse;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ProjectAdminService {

    private final ProjectUploadService projectUploadService;
    private final ProjectRepository projectRepository;

    public List<ProjectSimpleResponse> findAll() {
        List<Project> projects = projectRepository.findAll();
        return ProjectSimpleResponse.toList(projects);
    }

    public ProjectResponse findById(Long projectId) {
        Project project = projectRepository.findByIdFetchJoinPartnerships(projectId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 프로젝트는 없습니다."));
        return ProjectResponse.of(project);
    }

    public void uploadExcel(MultipartFile file) {
        projectUploadService.uploadProjects(file);
    }

    public void deleteById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 프로젝트는 없습니다."));
        projectRepository.delete(project);
    }
}
