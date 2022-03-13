package xyz.cryptohows.backend.admin.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VentureCapitalRequest {

    private String name;
    private String about;
    private String homepage;
    private String logo;

    public VentureCapitalRequest(String name, String about, String homepage, String logo) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
    }
}
