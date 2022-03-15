package xyz.cryptohows.backend.project.domain;

import lombok.Getter;

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

    public static List<String> getAllCategories() {
        return Arrays.stream(values())
                .map(Category::getCategoryName)
                .collect(Collectors.toList());
    }
}
