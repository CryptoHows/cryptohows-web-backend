package xyz.cryptohows.backend.admin.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class VentureCapitalNewListingRequest {

    private String name;
    private String about;
    private String homepage;
    private String logo;
    private MultipartFile projects;
    private MultipartFile rounds;

    public VentureCapitalNewListingRequest(String name, String about, String homepage, String logo, MultipartFile projects, MultipartFile rounds) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.projects = projects;
        this.rounds = rounds;
    }

    public VentureCapitalRequest extractVentureCapitalRequest() {
        return new VentureCapitalRequest(
                name,
                about,
                homepage,
                logo
        );
    }
}
