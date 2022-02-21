package xyz.cryptohows.backend.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectTest {

    private Project klaytn;

    private final String name = "클레이튼";
    private final String about = "클레이튼(Klaytn)은 ㈜카카오의 자회사인 그라운드엑스가 개발한 디앱(dApp·분산애플리케이션)을 위한 블록체인 플랫폼이다";
    private final String homepage = "https://www.klaytn.com/";
    private final Category category = Category.BLOCKCHAIN_INFRASTRUCTURE;
    private final Mainnet mainnet = Mainnet.KLAYTN;

    private final VentureCapital hashed = VentureCapital.builder()
            .name("해시드")
            .about("해시드 VC입니다.")
            .homepage("해시드.com")
            .logo("해시드.png")
            .build();

    private final VentureCapital a16z = VentureCapital.builder()
            .name("a16z")
            .about("a16z VC입니다.")
            .homepage("a16z.com")
            .logo("a16z.png")
            .build();

    @BeforeEach
    void setUp() {
        klaytn = Project.builder()
                .name(name)
                .about(about)
                .homepage(homepage)
                .category(category)
                .mainnet(mainnet)
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
        Project cryptohouse = Project.builder()
                .name("크립토하우스")
                .about("크립토하우스입니다.")
                .homepage("크립토하우스.com")
                .category(Category.SOCIAL_NETWORK)
                .mainnet(Mainnet.NONE)
                .build();

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

        assertThat(investors).containsExactly(hashed, a16z);
    }
}