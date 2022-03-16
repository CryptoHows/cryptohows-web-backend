package xyz.cryptohows.backend.project.application.filterStrategy;

import org.springframework.data.domain.Pageable;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

import java.util.List;

public class MainnetAndCategoryFilterStrategy extends FilterStrategy {

    public MainnetAndCategoryFilterStrategy(ProjectRepository projectRepository, RoundRepository roundRepository) {
        super(projectRepository, roundRepository);
    }

    @Override
    public List<Round> findRounds(String order, Integer page, Integer roundsPerPage, Mainnet mainnet, Category category) {
        Pageable pageable = generatePageable(order, page, roundsPerPage);
        return roundRepository.findRoundsFilterMainnetAndCategory(pageable, mainnet, category);
    }

    @Override
    public Long countAllRounds(Mainnet mainnet, Category category) {
        return roundRepository.countRoundsFilterMainnetAndCategory(mainnet, category);
    }
}
