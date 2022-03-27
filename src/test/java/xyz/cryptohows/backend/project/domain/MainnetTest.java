package xyz.cryptohows.backend.project.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.exception.CryptoHowsException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MainnetTest {

    @DisplayName("메인넷 빈칸은 NONE으로 저장된다.")
    @Test
    void emptyMainnet() {
        Mainnet mainnet1 = Mainnet.ofRegister(" ");
        Mainnet mainnet2 = Mainnet.ofRegister("   ");
        Mainnet mainnet3 = Mainnet.ofRegister("");
        Mainnet mainnet4 = Mainnet.ofRegister("     ");

        assertThat(mainnet1).isEqualTo(Mainnet.NONE);
        assertThat(mainnet2).isEqualTo(Mainnet.NONE);
        assertThat(mainnet3).isEqualTo(Mainnet.NONE);
        assertThat(mainnet4).isEqualTo(Mainnet.NONE);
    }

    @DisplayName("없는 메인넷은 예외가 발생한다.")
    @Test
    void notSupportedMainnet() {
        assertThatThrownBy(() -> Mainnet.ofRegister("helloMainnet"))
                .isInstanceOf(CryptoHowsException.class);
    }

    @DisplayName("parseIn을 통해 , 로 구분된 메인넷 구분자를 받아올 수 있다.")
    @Test
    void parseIn() {
        // given & when
        List<Mainnet> mainnets = Mainnet.parseIn("solana, klaytn, terra,ronin");

        // then
        assertThat(mainnets).containsExactly(Mainnet.SOLANA, Mainnet.KLAYTN, Mainnet.TERRA, Mainnet.RONIN);
    }

    @DisplayName("parseIn을 통해 , 로 구분된 메인넷 구분자를 받아올 수 있으며, 없는 친구는 그냥 무시된다.")
    @Test
    void parseInNoneNotencluded() {
        // given & when
        List<Mainnet> mainnets = Mainnet.parseIn("solana, klaytn, terra,ronin, nothing, hoho");

        // then
        assertThat(mainnets).containsExactly(Mainnet.SOLANA, Mainnet.KLAYTN, Mainnet.TERRA, Mainnet.RONIN);
    }
}
