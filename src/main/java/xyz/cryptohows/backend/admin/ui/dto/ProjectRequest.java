package xyz.cryptohows.backend.admin.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectRequest {

    private String name;
    private String about;
    private String homepage;
    private String logo;
    private String twitter;
    private String community;
    private String category;
    private String mainnet;
    private String partnerships;

    public ProjectRequest(String name, String about, String homepage, String logo, String twitter, String community,
                          String category, String mainnet, String partnerships) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.twitter = twitter;
        this.community = community;
        this.category = category;
        this.mainnet = mainnet;
        this.partnerships = partnerships;
    }

    public List<String> generateInvestors() {
        return Arrays.asList(partnerships.split(", "));
    }
}
