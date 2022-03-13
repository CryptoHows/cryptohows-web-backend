package xyz.cryptohows.backend.round.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.round.application.RoundService;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.ui.dto.RoundCountResponse;
import xyz.cryptohows.backend.round.ui.dto.RoundResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;

    @GetMapping("/rounds/recent")
    public ResponseEntity<List<RoundResponse>> findRecentRounds(@RequestParam Integer page,
                                                                @RequestParam(defaultValue = "10") Integer roundsPerPage) {
        List<RoundResponse> roundResponses = roundService.findRecentRounds(page, roundsPerPage);
        return ResponseEntity.ok(roundResponses);
    }

    @GetMapping("/rounds/old")
    public ResponseEntity<List<RoundResponse>> findOldRounds(@RequestParam Integer page,
                                                             @RequestParam(defaultValue = "10") Integer roundsPerPage) {
        List<RoundResponse> roundResponses = roundService.findOldRounds(page, roundsPerPage);
        return ResponseEntity.ok(roundResponses);
    }

    @GetMapping("/rounds/count")
    public ResponseEntity<RoundCountResponse> countRounds() {
        RoundCountResponse roundCounts = roundService.countRounds();
        return ResponseEntity.ok(roundCounts);
    }

    @GetMapping("/rounds/funding-stages")
    public ResponseEntity<List<String>> findFundingStages() {
        List<String> fundingStages = FundingStage.getAllFundingStages();
        return ResponseEntity.ok(fundingStages);
    }
}
