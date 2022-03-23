package xyz.cryptohows.backend.round.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.filtering.FilterStrategy;
import xyz.cryptohows.backend.filtering.FilterStrategyFactory;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.ui.dto.RoundPageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    public RoundPageResponse findRounds(String mainnet, String category, String order, Integer page, Integer roundsPerPage) {
        FilterStrategy filterStrategy = FilterStrategyFactory.of(mainnet, category, "").findStrategy();
        List<Mainnet> mainnets = Mainnet.parseIn(mainnet);
        List<Category> categories = Category.parseIn(category);
        Long count = filterStrategy.countAllRounds(mainnets, categories);
        List<Round> rounds = filterStrategy.findRounds(order, page, roundsPerPage, mainnets, categories);
        return RoundPageResponse.of(count, rounds);
    }
}
