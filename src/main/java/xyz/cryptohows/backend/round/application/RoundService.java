package xyz.cryptohows.backend.round.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.application.filterStrategy.FilterStrategy;
import xyz.cryptohows.backend.project.application.filterStrategy.FilterStrategyFactory;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.ui.dto.RoundPageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    public RoundPageResponse findRounds(String mainnet, String category, String order, Integer page, Integer roundsPerPage) {
        FilterStrategy filterStrategy = FilterStrategyFactory.of(mainnet, category).findStrategy();
        Long count = filterStrategy.countAllRounds(Mainnet.of(mainnet), Category.of(category));
        List<Round> rounds = filterStrategy.findRounds(order, page, roundsPerPage, Mainnet.of(mainnet), Category.of(category));
        return RoundPageResponse.of(count, rounds);
    }
}
