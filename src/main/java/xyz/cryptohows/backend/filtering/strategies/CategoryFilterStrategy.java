package xyz.cryptohows.backend.filtering.strategies;

import org.springframework.data.domain.Pageable;
import xyz.cryptohows.backend.filtering.FilterStrategy;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

import java.util.List;

public class CategoryFilterStrategy extends FilterStrategy {

    public CategoryFilterStrategy(ProjectRepository projectRepository, RoundRepository roundRepository) {
        super(projectRepository, roundRepository);
    }

    @Override
    public List<Round> findRounds(String order, Integer page, Integer roundsPerPage, List<Mainnet> mainnets, List<Category> categories) {
        Pageable pageable = generateRoundPageable(order, page, roundsPerPage);
        return roundRepository.findRoundsFilterCategory(pageable, categories);
    }

    @Override
    public Long countAllRounds(List<Mainnet> mainnets, List<Category> categories) {
        return roundRepository.countRoundsFilterCategory(categories);
    }

    @Override
    public List<Project> findProjects(Integer page, Integer projectsPerPage, List<Mainnet> mainnets, List<Category> categories) {
        Pageable pageable = generateProjectPageable(page, projectsPerPage);
        return projectRepository.findProjectsFilterCategory(pageable, categories);
    }

    @Override
    public Long countAllProjects(List<Mainnet> mainnets, List<Category> categories) {
        return projectRepository.countProjectsFilterCategory(categories);
    }
}
