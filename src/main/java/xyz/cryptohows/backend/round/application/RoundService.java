package xyz.cryptohows.backend.round.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.round.ui.dto.RoundCountResponse;
import xyz.cryptohows.backend.round.ui.dto.RoundResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public List<RoundResponse> findRecentRounds(Integer page, Integer roundsPerPage) {
        Pageable pageable = PageRequest.of(page, roundsPerPage, Sort.by("announcedDate").descending());
        List<Round> rounds = roundRepository.findRecentRounds(pageable);
        return RoundResponse.toList(rounds);
    }

    public List<RoundResponse> findOldRounds(Integer page, Integer roundsPerPage) {
        Pageable pageable = PageRequest.of(page, roundsPerPage, Sort.by("announcedDate").ascending());
        List<Round> rounds = roundRepository.findRecentRounds(pageable);
        return RoundResponse.toList(rounds);
    }

    public RoundCountResponse countRounds() {
        long roundCount = roundRepository.count();
        return new RoundCountResponse(roundCount);
    }
}
