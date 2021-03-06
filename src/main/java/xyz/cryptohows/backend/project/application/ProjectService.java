package xyz.cryptohows.backend.project.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.filtering.FilterStrategy;
import xyz.cryptohows.backend.filtering.FilterStrategyFactory;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.project.ui.dto.ProjectDetailResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectPageResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectSearchResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectPageResponse findProjects(String mainnet, String category, String ventureCapitals, Integer page, Integer projectsPerPage) {
        FilterStrategy filterStrategy = FilterStrategyFactory.of(mainnet, category, ventureCapitals).findStrategy();
        List<Mainnet> mainnets = Mainnet.parseIn(mainnet);
        List<Category> categories = Category.parseIn(category);
        Long count = filterStrategy.countAllProjects(mainnets, categories, ventureCapitals);
        List<Project> projects = filterStrategy.findProjects(page, projectsPerPage, mainnets, categories, ventureCapitals);
        return ProjectPageResponse.of(count, projects);
    }

    public ProjectDetailResponse findById(Long projectId) {
        Project project = projectRepository.findByIdFetchJoinPartnerships(projectId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 프로젝트는 없습니다."));
        return ProjectDetailResponse.of(project);
    }

    public List<ProjectSearchResponse> searchProjectContains(String searchWord) {
        List<Project> projects = projectRepository.findTop5ByNameStartsWithIgnoreCase(searchWord);
        return ProjectSearchResponse.toList(projects);
    }

    public List<String> getAllCategories() {
        List<Category> categories = projectRepository.findAllCategories();
        return Category.toStringList(categories);
    }

    public List<String> getAllMainnets() {
        List<Mainnet> mainnets = projectRepository.findAllMainnets();
        return Mainnet.toStringList(mainnets);
    }
}
