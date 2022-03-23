package xyz.cryptohows.backend.filtering.strategies;

import org.springframework.data.domain.Pageable;
import xyz.cryptohows.backend.filtering.FilterStrategy;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;

import java.util.List;

public class MainnetAndCategoryFilterStrategy extends FilterStrategy {

    public MainnetAndCategoryFilterStrategy(ProjectRepository projectRepository, PartnershipRepository partnershipRepository,
                                            RoundRepository roundRepository, RoundParticipationRepository roundParticipationRepository) {
        super(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Override
    public List<Round> findRounds(String order, Integer page, Integer roundsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        Pageable pageable = generatePageableSortByAnnouncedDate(order, page, roundsPerPage);
        return roundRepository.findRoundsFilterMainnetAndCategory(pageable, mainnets, categories);
    }

    @Override
    public Long countAllRounds(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        return roundRepository.countRoundsFilterMainnetAndCategory(mainnets, categories);
    }

    @Override
    public List<Project> findProjects(Integer page, Integer projectsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        Pageable pageable = generatePageableSortById(page, projectsPerPage);
        return projectRepository.findProjectsFilterMainnetAndCategory(pageable, mainnets, categories);
    }

    @Override
    public Long countAllProjects(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        return projectRepository.countProjectsFilterMainnetAndCategory(mainnets, categories);
    }
}
