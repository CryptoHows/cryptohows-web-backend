package xyz.cryptohows.backend.project.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.exception.CryptoHowsException;

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
}