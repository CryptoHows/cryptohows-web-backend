package xyz.cryptohows.backend.vc.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VentureCapitalTest {

    private VentureCapital hashed;

    private final String name = "해시드";
    private final String about = "해시드(Hashed)는 한국의 블록체인 분야의 전문 투자업체이다.";
    private final String homepage = "https://www.hashed.com/";
    private final String logo = "https://www.hashed.com/icons/icon-48x48.png?v=0d0c5de3e1ce3cc2a754603c645abcb9";

    Project cryptoHows = Project.builder()
            .name("cryptoHows")
            .about("VC가 투자한 프로젝트를 한눈에 볼 수 있어요")
            .homepage("https://cryptohows.xyz/")
            .category(Category.SOCIAL_NETWORK)
            .mainnet(Mainnet.NONE)
            .build();

    Project cryptoWhys = Project.builder()
            .name("cryptoWhys")
            .about("VC가 투자한 프로젝트를 한눈에 볼 수 있어요")
            .homepage("https://cryptoWhys.xyz/")
            .category(Category.SOCIAL_NETWORK)
            .mainnet(Mainnet.NONE)
            .build();

    @BeforeEach
    void setUp() {
        hashed = VentureCapital.builder()
                .name(name)
                .about(about)
                .homepage(homepage)
                .logo(logo)
                .build();
    }

    @Test
    @DisplayName("벤처 캐피탈의 정보를 담은 도메인를 받아올 수 있다.")
    void create() {
        assertThat(hashed.getName()).isEqualTo(name);
        assertThat(hashed.getAbout()).isEqualTo(about);
        assertThat(hashed.getHomepage()).isEqualTo(homepage);
        assertThat(hashed.getLogo()).isEqualTo(logo);
        assertThat(hashed.getPartnerships()).isEmpty();
    }

    @Test
    @DisplayName("벤처 캐피탈은 새로운 프로젝트와 파트너쉽을 맺을 수 있다")
    void partnership() {
        hashed.makePartnership(cryptoHows);

        List<Partnership> hashedPartnerships = hashed.getPartnerships();
        assertThat(hashedPartnerships).hasSize(1);
        assertThat(hashedPartnerships.get(0).getVentureCapital()).isEqualTo(hashed);
        assertThat(hashedPartnerships.get(0).getProject()).isEqualTo(cryptoHows);

        List<Partnership> cryptoHowsPartnerships = cryptoHows.getPartnerships();
        assertThat(cryptoHowsPartnerships).hasSize(1);
        assertThat(cryptoHowsPartnerships.get(0).getVentureCapital()).isEqualTo(hashed);
        assertThat(cryptoHowsPartnerships.get(0).getProject()).isEqualTo(cryptoHows);
    }

    @Test
    @DisplayName("벤처 캐피탈의 포트폴리오를 가져올 수 있다.")
    void getPortfolio() {
        hashed.makePartnerships(Arrays.asList(cryptoHows, cryptoWhys));

        List<Project> portfolio = hashed.getPortfolio();
        assertThat(portfolio).containsExactly(cryptoHows, cryptoWhys);
    }
}
