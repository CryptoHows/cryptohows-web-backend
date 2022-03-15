package xyz.cryptohows.backend.project.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.exception.CryptoHowsException;

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
}