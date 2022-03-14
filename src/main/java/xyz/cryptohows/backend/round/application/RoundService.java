package xyz.cryptohows.backend.round.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.round.application.filterStrategy.RoundFilterStrategy;
import xyz.cryptohows.backend.round.application.filterStrategy.RoundFilterStrategyFactory;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.ui.dto.RoundPageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    public RoundPageResponse findRounds(String mainnet, String category, String order, Integer page, Integer roundsPerPage) {
        RoundFilterStrategy roundFilterStrategy = RoundFilterStrategyFactory.of(mainnet, category).findStrategy();
        Long count = roundFilterStrategy.count(Mainnet.of(mainnet), Category.of(category));
        List<Round> rounds = roundFilterStrategy.findRounds(order, page, roundsPerPage, Mainnet.of(mainnet), Category.of(category));
        return RoundPageResponse.of(count, rounds);
    }
}
