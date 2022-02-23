package xyz.cryptohows.backend.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectTest {

    private Project klaytn;

    private final Project cryptohouse = Project.builder()
            .id(1L)
            .name("크립토하우스")
            .about("크립토하우스입니다.")
            .homepage("크립토하우스.com")
            .category(Category.SOCIAL_NETWORK)
            .mainnet(Mainnet.NONE)
            .build();

    private final VentureCapital hashed = VentureCapital.builder()
            .id(1L)
            .name("해시드")
            .about("해시드 VC입니다.")
            .homepage("해시드.com")
            .logo("해시드.png")
            .build();

    private final VentureCapital a16z = VentureCapital.builder()
            .id(2L)
            .name("a16z")
            .about("a16z VC입니다.")
            .homepage("a16z.com")
            .logo("a16z.png")
            .build();

    @BeforeEach
    void setUp() {
        klaytn = Project.builder()
                .name("클레이튼")
                .about("클레이튼(Klaytn)은 ㈜카카오의 자회사인 그라운드엑스가 개발한 디앱(dApp·분산애플리케이션)을 위한 블록체인 플랫폼이다")
                .homepage("https://www.klaytn.com/")
                .logo("https://www.klaytn.com/logo.png")
                .category(Category.BLOCKCHAIN_INFRASTRUCTURE)
                .mainnet(Mainnet.KLAYTN)
                .build();
    }

    @Test
    @DisplayName("프로젝트는 파트너쉽을 기록한다.")
    void addPartnership() {
        Partnership partnership = new Partnership(hashed, klaytn);
        klaytn.addPartnership(partnership);

        assertThat(klaytn.getPartnerships()).hasSize(1);
    }

    @Test
    @DisplayName("해당 프로젝트가 아닌 파트너쉽은 프로젝트에 기록할 수 없다.")
    void cannotAddPartnership() {
        Partnership partnership = new Partnership(hashed, cryptohouse);

        assertThatThrownBy(() -> klaytn.addPartnership(partnership))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 프로젝트의 투자사를 가져올 수 있다.")
    void getInvestors() {
        klaytn.addPartnership(new Partnership(hashed, klaytn));
        klaytn.addPartnership(new Partnership(a16z, klaytn));

        List<VentureCapital> investors = klaytn.getInvestors();

        assertThat(investors).containsExactlyInAnyOrder(hashed, a16z);
    }

    @Test
    @DisplayName("프로젝트에 라운드를 추가할 수 있다.")
    void addRound() {
        Round seed = Round.builder()
                .project(klaytn)
                .id(1L)
                .announcedDate("2019-03")
                .moneyRaised("$10M")
                .fundingStage(FundingStage.SEED)
                .build();

        klaytn.addRound(seed);
        assertThat(klaytn.getRounds()).hasSize(1);
    }

    @Test
    @DisplayName("자기 프로젝트의 라운드가 아니면 추가할 수 없다.")
    void cannotAddRound() {
        Round seriesA = Round.builder()
                .project(cryptohouse)
                .id(1L)
                .announcedDate("2020-01")
                .moneyRaised("$20M")
                .fundingStage(FundingStage.SERIES_A)
                .build();

        assertThatThrownBy(() -> klaytn.addRound(seriesA))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("여러 라운드가 진행되었다면 가장 최신 라운드를 반환할 수 있다.")
    void getRound() {
        Round seed = Round.builder()
                .id(1L)
                .project(klaytn)
                .announcedDate("2019-03")
                .moneyRaised("$10M")
                .fundingStage(FundingStage.SEED)
                .build();

        Round seriesA = Round.builder()
                .id(2L)
                .project(klaytn)
                .announcedDate("2020-01")
                .moneyRaised("$20M")
                .fundingStage(FundingStage.SERIES_A)
                .build();

        klaytn.addRound(seed);
        klaytn.addRound(seriesA);
        assertThat(klaytn.getCurrentRound()).isEqualTo(FundingStage.SERIES_A);
    }

    @Test
    @DisplayName("투자 받은 라운드가 없다면 최신 라운드 반환 시 NONE이 반환된다.")
    void getNoneRound() {
        assertThat(klaytn.getCurrentRound()).isEqualTo(FundingStage.NONE);
    }
}
