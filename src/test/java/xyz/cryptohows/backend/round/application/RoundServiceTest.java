package xyz.cryptohows.backend.round.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.cryptohows.backend.round.ui.dto.RoundPageResponse;

import static org.assertj.core.api.Assertions.assertThat;

class RoundServiceTest extends RoundServiceTestFixture {

    @Autowired
    private RoundService roundService;

    @DisplayName("mainnet, category, 페이지네이션, 최신순으로 라운드를 구할 수 있다")
    @Test
    void findRoundsMainnetAndCategoryRecent() {
        // given
        String mainnet = "terra";
        String category = "infrastructure";
        String order = "recent";

        // when
        RoundPageResponse response = roundService.findRounds(mainnet, category, "", order, 0, 10);

        // then
        assertThat(response.getRounds()).hasSize(2);
        assertThat(response.getRounds().get(0).getId()).isEqualTo(lunaSeriesC.getId());
        assertThat(response.getRounds().get(1).getId()).isEqualTo(lunaSeriesB.getId());
        assertThat(response.getTotalRounds()).isEqualTo(2L);
    }

    @DisplayName("mainnet, category, 페이지네이션, 오래된 순으로 라운드를 구할 수 있다")
    @Test
    void findRoundsMainnetAndCategoryOld() {
        // given
        String mainnet = "terra";
        String category = "infrastructure";
        String order = "old";

        // when
        RoundPageResponse response = roundService.findRounds(mainnet, category, "", order, 0, 10);

        // then
        assertThat(response.getRounds()).hasSize(2);
        assertThat(response.getRounds().get(0).getId()).isEqualTo(lunaSeriesB.getId());
        assertThat(response.getRounds().get(1).getId()).isEqualTo(lunaSeriesC.getId());
        assertThat(response.getTotalRounds()).isEqualTo(2L);
    }

    @DisplayName("여러개의 mainnet, category, 페이지네이션, 오래된 순으로 라운드를 구할 수 있다")
    @Test
    void findRoundsMultipleMainnetAndCategoryOld() {
        // given
        String mainnet = "terra, solana";
        String category = "infrastructure";
        String order = "old";

        // when
        RoundPageResponse response = roundService.findRounds(mainnet, category, "", order, 0, 10);

        // then
        assertThat(response.getRounds()).hasSize(4);
        assertThat(response.getRounds().get(0).getId()).isEqualTo(lunaSeriesB.getId());
        assertThat(response.getRounds().get(1).getId()).isEqualTo(lunaSeriesC.getId());
        assertThat(response.getRounds().get(2).getId()).isEqualTo(SolanaSeed.getId());
        assertThat(response.getRounds().get(3).getId()).isEqualTo(SolanaSeriesA.getId());
        assertThat(response.getTotalRounds()).isEqualTo(4L);
    }

    @DisplayName("category, 페이지네이션으로 라운드를 구할 수 있다")
    @Test
    void findRoundsCategoryOnly() {
        // given
        String mainnet = "";
        String category = "infrastructure";
        String order = "recent";

        // when
        RoundPageResponse response = roundService.findRounds(mainnet, category, "", order, 0, 10);

        // then
        assertThat(response.getRounds()).hasSize(4);
        assertThat(response.getRounds().get(0).getId()).isEqualTo(SolanaSeriesA.getId());
        assertThat(response.getRounds().get(1).getId()).isEqualTo(SolanaSeed.getId());
        assertThat(response.getRounds().get(2).getId()).isEqualTo(lunaSeriesC.getId());
        assertThat(response.getRounds().get(3).getId()).isEqualTo(lunaSeriesB.getId());
        assertThat(response.getTotalRounds()).isEqualTo(4L);
    }

    @DisplayName("여러개의 category, 페이지네이션으로 라운드를 구할 수 있다")
    @Test
    void findRoundsMultipleCategoryOnly() {
        // given
        String mainnet = "";
        String category = "infrastructure, web3";
        String order = "recent";

        // when
        RoundPageResponse response = roundService.findRounds(mainnet, category, "", order, 0, 10);

        // then
        assertThat(response.getRounds()).hasSize(6);
        assertThat(response.getRounds().get(0).getId()).isEqualTo(axieSeriesA.getId());
        assertThat(response.getRounds().get(1).getId()).isEqualTo(SolanaSeriesA.getId());
        assertThat(response.getRounds().get(2).getId()).isEqualTo(axieSeed.getId());
        assertThat(response.getRounds().get(3).getId()).isEqualTo(SolanaSeed.getId());
        assertThat(response.getRounds().get(4).getId()).isEqualTo(lunaSeriesC.getId());
        assertThat(response.getRounds().get(5).getId()).isEqualTo(lunaSeriesB.getId());
        assertThat(response.getTotalRounds()).isEqualTo(6L);
    }

    @DisplayName("mainnet, 페이지네이션, 오래된 순으로 라운드를 구할 수 있다")
    @Test
    void findRoundsCategory() {
        // given
        String mainnet = "ethereum";
        String category = "";
        String order = "recent";

        // when
        RoundPageResponse response = roundService.findRounds(mainnet, category, "", order, 0, 10);

        // then
        assertThat(response.getRounds()).hasSize(2);
        assertThat(response.getRounds().get(0).getId()).isEqualTo(axieSeriesA.getId());
        assertThat(response.getRounds().get(1).getId()).isEqualTo(axieSeed.getId());
        assertThat(response.getTotalRounds()).isEqualTo(2L);
    }

    @DisplayName("최신 순으로 라운드를 구할 수 있다")
    @Test
    void findRounds() {
        // given
        String mainnet = "";
        String category = "";
        String order = "recent";

        // when
        RoundPageResponse response = roundService.findRounds(mainnet, category, "", order, 0, 10);

        // then
        assertThat(response.getRounds()).hasSize(6);
        assertThat(response.getRounds().get(0).getId()).isEqualTo(axieSeriesA.getId());
        assertThat(response.getRounds().get(1).getId()).isEqualTo(SolanaSeriesA.getId());
        assertThat(response.getRounds().get(2).getId()).isEqualTo(axieSeed.getId());
        assertThat(response.getRounds().get(3).getId()).isEqualTo(SolanaSeed.getId());
        assertThat(response.getRounds().get(4).getId()).isEqualTo(lunaSeriesC.getId());
        assertThat(response.getRounds().get(5).getId()).isEqualTo(lunaSeriesB.getId());
        assertThat(response.getTotalRounds()).isEqualTo(6L);
    }
}
