package xyz.cryptohows.backend.round.application.filterStrategy;

import org.springframework.data.domain.Pageable;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

import java.util.List;

public class CategoryFilterStrategy extends RoundFilterStrategy {

    public CategoryFilterStrategy(RoundRepository roundRepository) {
        super(roundRepository);
    }

    @Override
    public List<Round> findRounds(String order, Integer page, Integer roundsPerPage, Mainnet mainnet, Category category) {
        Pageable pageable = generatePageable(order, page, roundsPerPage);
        return roundRepository.findRoundsFilterCategory(pageable, category);
    }

    @Override
    public Long count(Mainnet mainnet, Category category) {
        return roundRepository.countRoundsFilterCategory(category);
    }
}
