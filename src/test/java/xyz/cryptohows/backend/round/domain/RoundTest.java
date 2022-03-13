package xyz.cryptohows.backend.round.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoundTest {

    private Round round;

    private Project cryptohouse = Project.builder()
            .id(1L)
            .name("크립토하우스")
            .about("크립토하우스입니다.")
            .homepage("크립토하우스.com")
            .logo("크립토하우스.png")
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
        round = Round.builder()
                .id(1L)
                .project(cryptohouse)
                .announcedDate("2019-03")
                .moneyRaised("$10M")
                .newsArticle("https://news.com/funding")
                .fundingStage(FundingStage.SEED)
                .build();
    }

    @Test
    @DisplayName("해당 라운드에 VC가 펀딩 할 수 있다.")
    void vcJoinRound() {
        round.makeParticipation(hashed);
        assertThat(round.getVcParticipants()).hasSize(1);
    }

    @Test
    @DisplayName("해당 라운드에 여러 VC가 동시에 펀딩할 수 있다.")
    void multipleVCJoinRound() {
        round.makeParticipations(Arrays.asList(hashed, a16z));
        assertThat(round.getVcParticipants()).hasSize(2);
    }

    @Test
    @DisplayName("해당 라운드에 참여한 VC를 알 수 있다.")
    void getParticipatedVC() {
        round.makeParticipations(Arrays.asList(hashed, a16z));

        List<VentureCapital> participants = round.getParticipatedVC();
        assertThat(participants).containsExactlyInAnyOrder(hashed, a16z);
    }
}
