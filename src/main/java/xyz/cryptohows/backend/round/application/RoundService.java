package xyz.cryptohows.backend.round.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.round.ui.dto.RoundResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public List<RoundResponse> findRecentRounds() {
        List<Round> rounds = roundRepository.findRecentRounds();
        return RoundResponse.toList(rounds);
    }
}
