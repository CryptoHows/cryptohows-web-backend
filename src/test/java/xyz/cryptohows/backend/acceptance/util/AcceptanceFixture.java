package xyz.cryptohows.backend.acceptance.util;

import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

public class AcceptanceFixture {

    private AcceptanceFixture() {
    }

    public static final VentureCapital A16Z = VentureCapital.builder()
            .name("a16z")
            .about("미국 VC")
            .homepage("a16z.com")
            .logo("a16z.png")
            .build();

    public static final VentureCapital 해시드 = VentureCapital.builder()
            .name("해시드")
            .about("한국의 VC")
            .homepage("hashed.com")
            .logo("hashed.png")
            .build();

    public static final Project 이더리움 = Project.builder()
            .name("ETHEREUM")
            .about("ETHEREUM 프로젝트")
            .homepage("https://ETHEREUM.io/")
            .logo("https://ETHEREUM.io/logo.png")
            .category(Category.INFRASTRUCTURE)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    public static final Project 이오스 = Project.builder()
            .name("EOS")
            .about("EOS 프로젝트")
            .homepage("https://EOS.io/")
            .category(Category.INFRASTRUCTURE)
            .mainnet(Mainnet.EOS)
            .build();

    public static final Project 엑시인피니티 = Project.builder()
            .name("axieInfinity")
            .about("엑시 인피니티")
            .homepage("https://axieInfinity.xyz/")
            .category(Category.WEB3)
            .mainnet(Mainnet.ETHEREUM)
            .build();

    public static final Round 이더리움_시드 = Round.builder()
            .project(이더리움)
            .announcedDate("2016-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    public static final Round 이더리움_시리즈A = Round.builder()
            .project(이더리움)
            .announcedDate("2017-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    public static final Round 이오스_시드 = Round.builder()
            .project(이오스)
            .announcedDate("2019-10")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    public static final Round 이오스_시리즈A = Round.builder()
            .project(이오스)
            .announcedDate("2020-02")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();

    public static final Round 엑시_시드 = Round.builder()
            .project(엑시인피니티)
            .announcedDate("2019-11")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SEED)
            .build();

    public static final Round 엑시_시리즈A = Round.builder()
            .project(엑시인피니티)
            .announcedDate("2020-03")
            .moneyRaised("$20M")
            .newsArticle("https://news.com/funding")
            .fundingStage(FundingStage.SERIES_A)
            .build();
}
