package xyz.cryptohows.backend.round.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.round.application.RoundService;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.ui.dto.RoundPageResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;

    @GetMapping("/rounds")
    public ResponseEntity<RoundPageResponse> findRounds(@RequestParam(defaultValue = "") String mainnet,
                                                        @RequestParam(defaultValue = "") String category,
                                                        @RequestParam(defaultValue = "") String ventureCapitals,
                                                        @RequestParam(defaultValue = "recent") String order,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer roundsPerPage) {
        RoundPageResponse roundPageResponse = roundService.findRounds(mainnet, category, ventureCapitals, order, page, roundsPerPage);
        return ResponseEntity.ok(roundPageResponse);
    }

    @GetMapping("/rounds/coin-available")
    public ResponseEntity<RoundPageResponse> findCoinAvailableRounds(@RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "10") Integer roundsPerPage) {
        RoundPageResponse roundPageResponse = roundService.findCoinAvailableRounds(page, roundsPerPage);
        return ResponseEntity.ok(roundPageResponse);
    }

    @GetMapping("/rounds/funding-stages")
    public ResponseEntity<List<String>> findFundingStages() {
        List<String> fundingStages = FundingStage.getAllFundingStages();
        return ResponseEntity.ok(fundingStages);
    }
}
