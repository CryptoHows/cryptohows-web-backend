package xyz.cryptohows.backend.admin.ui.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoundRequestTest {

    @DisplayName("moneyRaised를 달러로 변환할 수 있다.")
    @Test
    void formatMoneyRaised() {
        // given
        RoundRequest request = new RoundRequest("project", "2022-01-03", "1000000", "news", "seed", "hashed");

        // when
        String formatted = request.formatMoneyRaised();

        // then
        assertThat(formatted).isEqualTo("$1,000,000");
    }
}
