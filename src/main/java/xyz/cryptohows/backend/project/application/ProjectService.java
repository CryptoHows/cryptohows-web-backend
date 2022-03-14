package xyz.cryptohows.backend.project.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.project.ui.dto.ProjectDetailResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> findProjects(Integer page, Integer projectPerPage) {
        Pageable pageable = PageRequest.of(page, projectPerPage);
        List<Project> projects = projectRepository.findProjectsFetchJoinPartnerships(pageable);
        return ProjectResponse.toList(projects);
    }

    public ProjectDetailResponse findById(Long projectId) {
        Project project = projectRepository.findByIdFetchJoinPartnerships(projectId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 프로젝트는 없습니다."));
        return ProjectDetailResponse.of(project);
    }
}
