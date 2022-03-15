package xyz.cryptohows.backend.project.domain;

import lombok.Getter;
import xyz.cryptohows.backend.exception.CryptoHowsException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Category {
    NONE("None"),
    CEFI("CeFi"),
    DEFI("DeFi"),
    INFRASTRUCTURE("Infrastructure"),
    NFTS("NFTs"),
    WEB3("Web3");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category of(String input) {
        return Arrays.stream(values())
                .filter(category -> category.categoryName.equalsIgnoreCase(input))
                .findAny()
                .orElse(NONE);
    }

    public static Category ofRegister(String input) {
        String inputCategory = input.trim();
        if (inputCategory.isEmpty()) {
            return NONE;
        }
        return Arrays.stream(values())
                .filter(category -> category.categoryName.equalsIgnoreCase(inputCategory))
                .findAny()
                .orElseThrow(() -> new CryptoHowsException(inputCategory + "은 카테고리에 저장되어 있지 않습니다."));
    }

    public static List<String> getAllCategories() {
        return Arrays.stream(values())
                .filter(Category::isNotNone)
                .map(Category::getCategoryName)
                .collect(Collectors.toList());
    }

    private static boolean isNotNone(Category category) {
        return category != Category.NONE;
    }
}
