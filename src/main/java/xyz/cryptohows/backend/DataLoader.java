package xyz.cryptohows.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.transaction.Transactional;
import java.util.Arrays;

@Profile("local")
@Transactional
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final ProjectRepository projectRepository;
    private final VentureCapitalRepository ventureCapitalRepository;
    private final PartnershipRepository partnershipRepository;
    private final RoundRepository roundRepository;
    private final RoundParticipationRepository roundParticipationRepository;

    @Override
    public void run(ApplicationArguments args) {

        VentureCapital hashed = VentureCapital.builder()
                .name("해시드")
                .about("해시드(Hashed)는 한국의 블록체인 분야의 전문 투자업체이다.")
                .homepage("https://www.hashed.com/")
                .logo("https://cdn.bloomingbit.io/download.php?fid=ZnJCQUBmczEuYmxvb21pbmdiaXQuaW86L0FSVElDTEUvMTAvMTA2Ny5wbmc=")
                .build();

        VentureCapital a16z = VentureCapital.builder()
                .name("a16z")
                .about("a16z는 앤드리슨 호로위츠는 마크 앤드리슨과 벤 호로위츠가 공동 창업한 미국의 IT 벤처 투자 전문 회사이다.")
                .homepage("https://a16z.com/")
                .logo("https://pbs.twimg.com/profile_images/1359170208879968256/GrGmuXo0_400x400.png")
                .build();
        ventureCapitalRepository.saveAll(Arrays.asList(hashed, a16z));


        Project klaytn = Project.builder()
                .name("클레이튼")
                .about("클레이튼(Klaytn)은 ㈜카카오의 자회사인 그라운드엑스가 개발한 디앱(dApp·분산애플리케이션)을 위한 블록체인 플랫폼이다")
                .homepage("https://www.klaytn.com/")
                .logo("https://www.theguru.co.kr/data/photos/20220102/art_16420309738883_e42c48.png")
                .category(Category.INFRASTRUCTURE)
                .mainnet(Mainnet.KLAYTN)
                .build();

        Project EOS = Project.builder()
                .name("EOS")
                .about("위임지분증명(DPoS) 방식, 이더리움의 느린/비싼 처리 해결 대안")
                .homepage("https://EOS.io/")
                .logo("https://t1.daumcdn.net/cfile/tistory/99913D455B351BD601")
                .category(Category.INFRASTRUCTURE)
                .mainnet(Mainnet.EOS)
                .build();

        Project axieInfinity = Project.builder()
                .name("Axie Infinity")
                .about("베트남 스카이마비스에서 개발한 엑시 인피니티 게임")
                .homepage("https://axieinfinity.com/")
                .logo("https://techvodoo.com/wp-content/uploads/2021/08/Axie-Infinity-1.jpg")
                .category(Category.WEB3)
                .mainnet(Mainnet.ETHEREUM)
                .build();
        projectRepository.saveAll(Arrays.asList(klaytn, EOS, axieInfinity));


        Partnership hashedKlaytn = new Partnership(hashed, klaytn);
        Partnership hashedEOS = new Partnership(hashed, EOS);
        Partnership hashedAxieInfinity = new Partnership(hashed, axieInfinity);

        Partnership a16zEOS = new Partnership(a16z, EOS);
        Partnership a16zAxieInfinity = new Partnership(a16z, axieInfinity);
        partnershipRepository.saveAll(Arrays.asList(hashedKlaytn, hashedEOS, hashedAxieInfinity, a16zEOS, a16zAxieInfinity));


        Round klaytnSeed = Round.builder()
                .project(klaytn)
                .announcedDate("2019-03")
                .moneyRaised("$90M")
                .newsArticle("https://www.finyear.com/Klaytn-raises-90-million-in-seed-funding-to-drive-the-mainstream-adoption-of-blockchain_a41034.html")
                .fundingStage(FundingStage.SEED)
                .build();

        Round klaytnSeriesA = Round.builder()
                .project(klaytn)
                .announcedDate("2020-03")
                .moneyRaised("$100M")
                .newsArticle("https://www.finyear.com/Klaytn-raises-90-million-in-seed-funding-to-drive-the-mainstream-adoption-of-blockchain_a41034.html")
                .fundingStage(FundingStage.SERIES_A)
                .build();

        Round EOSICO = Round.builder()
                .project(EOS)
                .announcedDate("2019-09")
                .moneyRaised("$4B")
                .newsArticle("https://www.coindesk.com/markets/2019/09/17/the-first-yearlong-ico-for-eos-raised-4-billion-the-second-just-28-million/")
                .fundingStage(FundingStage.ICO)
                .build();

        Round axieInfinitySeriesB = Round.builder()
                .project(axieInfinity)
                .announcedDate("2021-10")
                .moneyRaised("$150M")
                .newsArticle("https://www.coindesk.com/business/2021/10/04/axie-infinity-to-raise-150m-series-b-at-3b-valuation-report/")
                .fundingStage(FundingStage.SERIES_B)
                .build();
        roundRepository.saveAll(Arrays.asList(klaytnSeed, klaytnSeriesA, EOSICO, axieInfinitySeriesB));


        RoundParticipation hashedKlaytnSeed = new RoundParticipation(hashed, klaytnSeed);
        RoundParticipation hashedKlaytnSeriesA = new RoundParticipation(hashed, klaytnSeriesA);
        RoundParticipation hashedEOSICO = new RoundParticipation(hashed, EOSICO);
        RoundParticipation hashedaxieInfinitySeriesB = new RoundParticipation(hashed, axieInfinitySeriesB);

        RoundParticipation a16zEOSICO = new RoundParticipation(a16z, EOSICO);
        RoundParticipation a16zaxieInfinitySeriesB = new RoundParticipation(a16z, axieInfinitySeriesB);
        roundParticipationRepository.saveAll(Arrays.asList(hashedKlaytnSeed, hashedKlaytnSeriesA, hashedEOSICO,
                hashedaxieInfinitySeriesB, a16zEOSICO, a16zaxieInfinitySeriesB));
    }
}
