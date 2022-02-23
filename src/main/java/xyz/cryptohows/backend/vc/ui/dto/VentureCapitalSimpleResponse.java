package xyz.cryptohows.backend.vc.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VentureCapitalSimpleResponse {

    private final Long id;
    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;

    public VentureCapitalSimpleResponse(Long id, String name, String about, String homepage, String logo) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
    }

    private static VentureCapitalSimpleResponse of(VentureCapital ventureCapital) {
        return new VentureCapitalSimpleResponse(
                ventureCapital.getId(),
                ventureCapital.getName(),
                ventureCapital.getAbout(),
                ventureCapital.getHomepage(),
                ventureCapital.getLogo()
        );
    }

    public static List<VentureCapitalSimpleResponse> toList(List<VentureCapital> ventureCapitals) {
        return ventureCapitals.stream()
                .map(VentureCapitalSimpleResponse::of)
                .collect(Collectors.toList());
    }
}
