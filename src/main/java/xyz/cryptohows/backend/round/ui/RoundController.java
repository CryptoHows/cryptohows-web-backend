package xyz.cryptohows.backend.round.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.round.application.RoundService;
import xyz.cryptohows.backend.round.ui.dto.RoundResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;

    @GetMapping("/rounds/recent")
    public ResponseEntity<List<RoundResponse>> findRecentRounds() {
        List<RoundResponse> roundResponses = roundService.findRecentRounds();
        return ResponseEntity.ok(roundResponses);
    }
}
