package xyz.cryptohows.backend.round.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.filtering.FilterStrategy;
import xyz.cryptohows.backend.filtering.FilterStrategyFactory;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.round.ui.dto.RoundPageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    public final RoundRepository roundRepository;

    public RoundPageResponse findRounds(String mainnet, String category, String ventureCapitals, String order, Integer page, Integer roundsPerPage) {
        FilterStrategy filterStrategy = FilterStrategyFactory.of(mainnet, category, ventureCapitals).findStrategy();
        List<Mainnet> mainnets = Mainnet.parseIn(mainnet);
        List<Category> categories = Category.parseIn(category);
        Long count = filterStrategy.countAllRounds(mainnets, categories, ventureCapitals);
        List<Round> rounds = filterStrategy.findRounds(order, page, roundsPerPage, mainnets, categories, ventureCapitals);
        return RoundPageResponse.of(count, rounds);
    }

    public RoundPageResponse findCoinAvailableRounds(Integer page, Integer roundsPerPage) {
        Pageable pageable = PageRequest.of(page, roundsPerPage);
        Long count = roundRepository.countCoinAvailableRounds();
        List<Round> rounds= roundRepository.findCoinAvailableRoundsSortByRecent(pageable);
        return RoundPageResponse.of(count, rounds);
    }
}
