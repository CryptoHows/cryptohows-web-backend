package xyz.cryptohows.backend.round.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class RoundRepositoryTest {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundParticipationRepository roundParticipationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    private TestEntityManager tem;

    private final VentureCapital hashed = VentureCapital.builder()
            .name("해시드")
            .about("한국의 VC")
            .homepage("hashed.com")
            .logo("hashed.png")
            .build();

    private final Project EOS = Project.builder()
            .name("EOS")
            .about("EOS 프로젝트")
            .homepage("https://EOS.io/")
            .category(Category.BLOCKCHAIN_INFRASTRUCTURE)
            .mainnet(Mainnet.EOS)
            .build();

    private final Project axieInfinity = Project.builder()
            .name("axieInfinity")
            .about("엑시 인피니티")
            .homepage("https://axieInfinity.xyz/")
            .category(Category.GAMING)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    private final Round EOSSeed = Round.builder()
            .project(EOS)
            .announcedDate("2019-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    private final Round axieSeed = Round.builder()
            .project(axieInfinity)
            .announcedDate("2019-11")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    private final Round EOSSeriesA = Round.builder()
            .project(EOS)
            .announcedDate("2020-02")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    private final Round axieSeriesA = Round.builder()
            .project(axieInfinity)
            .announcedDate("2020-03")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    @BeforeEach
    void setUp() {
        projectRepository.saveAll(Arrays.asList(EOS, axieInfinity));
        ventureCapitalRepository.save(hashed);

        roundRepository.save(EOSSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeed));

        roundRepository.save(axieSeed);
        roundParticipationRepository.save(new RoundParticipation(hashed, axieSeed));

        roundRepository.save(EOSSeriesA);
        roundParticipationRepository.save(new RoundParticipation(hashed, EOSSeriesA));

        roundRepository.save(axieSeriesA);
        roundParticipationRepository.save(new RoundParticipation(hashed, axieSeriesA));

        tem.flush();
        tem.clear();
    }

    @Test
    @DisplayName("Round의 Project가 없다면 저장되지 않는다.")
    void cannotSaveRound() {
        Round noProject = Round.builder()
                .announcedDate("2019-10")
                .moneyRaised("$20M")
                .newsArticle("https://news.com/funding")
                .fundingStage(FundingStage.SEED)
                .build();

        assertThatThrownBy(() -> roundRepository.save(noProject))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("해당 Round에 벤처캐피탈이 참여할 수 있다.")
    void vcJoinsRound() {
        Round round = roundRepository.findById(EOSSeed.getId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(round.getParticipants()).hasSize(1);
    }

    @Test
    @DisplayName("해당 라운드가 삭제되면, 그에 따른 RoundParticipant 모두 삭제된다.")
    void roundDeleted() {
        // when
        long countBefore = roundParticipationRepository.count();
        roundRepository.deleteById(EOSSeed.getId());
        long countAfter = roundParticipationRepository.count();

        // then
        assertThat(countBefore).isEqualTo(4L);
        assertThat(countAfter).isEqualTo(3L);
    }

    @Test
    @DisplayName("최근에 투자받은 라운드 순서대로 페이지네이션을 통해 받아볼 수 있다.")
    void recentRounds() {
        // when
        Pageable pageable1 = PageRequest.of(0, 2, Sort.by("announcedDate").descending());
        List<Round> recentRounds1 = roundRepository.findRecentRounds(pageable1);

        Pageable pageable2 = PageRequest.of(1, 2, Sort.by("announcedDate").descending());
        List<Round> recentRounds2 = roundRepository.findRecentRounds(pageable2);

        // then
        assertThat(recentRounds1.get(0)).isEqualTo(axieSeriesA);
        assertThat(recentRounds1.get(1)).isEqualTo(EOSSeriesA);

        assertThat(recentRounds2.get(0)).isEqualTo(axieSeed);
        assertThat(recentRounds2.get(1)).isEqualTo(EOSSeed);
    }

    @Test
    @DisplayName("투자받은 라운드 오래된 순서대로 페이지네이션을 통해 받아볼 수 있다.")
    void recentRoundsASC() {
        // when
        Pageable pageable1 = PageRequest.of(0, 2, Sort.by("announcedDate").ascending());
        List<Round> oldRounds1 = roundRepository.findRecentRounds(pageable1);

        Pageable pageable2 = PageRequest.of(1, 2, Sort.by("announcedDate").ascending());
        List<Round> oldRounds2 = roundRepository.findRecentRounds(pageable2);

        // then
        assertThat(oldRounds1.get(0)).isEqualTo(EOSSeed);
        assertThat(oldRounds1.get(1)).isEqualTo(axieSeed);

        assertThat(oldRounds2.get(0)).isEqualTo(EOSSeriesA);
        assertThat(oldRounds2.get(1)).isEqualTo(axieSeriesA);
    }

    @Test
    @DisplayName("라운드의 카운트를 셀 수 있다.")
    void countRound() {
        // when
        long count = roundRepository.count();

        // then
        assertThat(count).isEqualTo(4L);
    }
}
