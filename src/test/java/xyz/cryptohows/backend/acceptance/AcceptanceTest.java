package xyz.cryptohows.backend.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import xyz.cryptohows.backend.acceptance.util.DatabaseCleanup;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import java.util.Arrays;

import static xyz.cryptohows.backend.acceptance.util.AcceptanceFixture.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected VentureCapitalRepository ventureCapitalRepository;

    @Autowired
    protected PartnershipRepository partnershipRepository;

    @Autowired
    protected RoundRepository roundRepository;

    @Autowired
    protected RoundParticipationRepository roundParticipationRepository;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        uploadTestData();
    }

    private void uploadTestData() {
        ventureCapitalRepository.saveAll(Arrays.asList(해시드, A16Z));

        projectRepository.saveAll(Arrays.asList(이더리움, 이오스, 엑시인피니티));

        partnershipRepository.saveAll(Arrays.asList(
                new Partnership(해시드, 이더리움),
                new Partnership(해시드, 이오스),
                new Partnership(해시드, 엑시인피니티),
                new Partnership(A16Z, 이더리움),
                new Partnership(A16Z, 이오스)
        ));

        roundRepository.saveAll(Arrays.asList(이더리움_시드, 이더리움_시리즈A, 이오스_시드, 이오스_시리즈A, 엑시_시드, 엑시_시리즈A));

        roundParticipationRepository.saveAll(Arrays.asList(
                new RoundParticipation(해시드, 이더리움_시드),
                new RoundParticipation(A16Z, 이더리움_시드),

                new RoundParticipation(해시드, 이더리움_시리즈A),
                new RoundParticipation(A16Z, 이더리움_시리즈A),

                new RoundParticipation(해시드, 이오스_시드),
                new RoundParticipation(A16Z, 이오스_시드),

                new RoundParticipation(A16Z, 이오스_시리즈A),

                new RoundParticipation(해시드, 엑시_시드),
                new RoundParticipation(해시드, 엑시_시리즈A)
        ));
    }
}
