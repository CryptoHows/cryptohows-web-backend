package xyz.cryptohows.backend.project.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.application.filterStrategy.FilterStrategy;
import xyz.cryptohows.backend.project.application.filterStrategy.FilterStrategyFactory;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.project.ui.dto.ProjectDetailResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectPageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectPageResponse findProjects(String mainnet, String category, Integer page, Integer projectPerPage) {
        FilterStrategy filterStrategy = FilterStrategyFactory.of(mainnet, category).findStrategy();
        Pageable pageable = PageRequest.of(page, projectPerPage);
        List<Project> projects = projectRepository.findProjectsFetchJoinPartnerships(pageable);
        long totalProjectCount = projectRepository.count();
        return ProjectPageResponse.of(totalProjectCount, projects);
    }

    public ProjectDetailResponse findById(Long projectId) {
        Project project = projectRepository.findByIdFetchJoinPartnerships(projectId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 프로젝트는 없습니다."));
        return ProjectDetailResponse.of(project);
    }
}
