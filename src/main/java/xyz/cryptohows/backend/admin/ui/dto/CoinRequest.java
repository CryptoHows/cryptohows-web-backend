package xyz.cryptohows.backend.admin.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoinRequest {

    private String project;
    private String coinSymbol;
    private String coinInformation;

    public CoinRequest(String project, String coinSymbol, String coinInformation) {
        this.project = project;
        this.coinSymbol = coinSymbol;
        this.coinInformation = coinInformation;
    }
}
