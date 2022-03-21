package xyz.cryptohows.backend.project.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.exception.CryptoHowsException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryTest {

    @DisplayName("카테고리 빈칸은 NONE으로 저장된다.")
    @Test
    void emptyCategory() {
        Category category1 = Category.ofRegister("");
        Category category2 = Category.ofRegister(" ");
        Category category3 = Category.ofRegister("   ");

        assertThat(category1).isEqualTo(Category.NONE);
        assertThat(category2).isEqualTo(Category.NONE);
        assertThat(category3).isEqualTo(Category.NONE);
    }

    @DisplayName("없는 카테고리는 예외가 발생한다.")
    @Test
    void notSupportedCategory() {
        assertThatThrownBy(() -> Category.ofRegister("Not Category"))
                .isInstanceOf(CryptoHowsException.class);
    }

    @DisplayName("parseIn을 통해 요청 들어온 것을 파싱할 수 있다.")
    @Test
    void parseIn() {
        // given & when
        List<Category> categories = Category.parseIn("cefi, defi, web3");

        // then
        assertThat(categories).containsExactly(Category.CEFI, Category.DEFI, Category.WEB3);
    }

    @DisplayName("parseIn을 통해 요청 들어온 것을 파싱할 수 있으며, 없는 카테고리는 무시된다.")
    @Test
    void parseInNothing() {
        // given & when
        List<Category> categories = Category.parseIn("cefi, defi, web3, web41");

        // then
        assertThat(categories).containsExactly(Category.CEFI, Category.DEFI, Category.WEB3);
    }

    @DisplayName("toStringList를 통해 None을 제외한 카테고리를 알파벳 순서로 받아볼 수 있다.")
    @Test
    void toStringList() {
        List<Category> categories = Arrays.asList(Category.CEFI, Category.NFTS, Category.NONE, Category.DEFI, Category.INFRASTRUCTURE, Category.WEB3);

        List<String> strings = Category.toStringList(categories);

        assertThat(strings).containsExactly("CeFi", "DeFi", "Infrastructure", "NFTs", "Web3");
    }
}
