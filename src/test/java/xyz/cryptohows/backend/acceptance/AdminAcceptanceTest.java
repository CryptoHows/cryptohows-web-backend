package xyz.cryptohows.backend.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import xyz.cryptohows.backend.admin.ui.dto.AdminLoginRequest;
import xyz.cryptohows.backend.admin.ui.dto.ProjectRequest;
import xyz.cryptohows.backend.admin.ui.dto.RoundRequest;
import xyz.cryptohows.backend.admin.ui.dto.VentureCapitalRequest;
import xyz.cryptohows.backend.auth.ui.dto.TokenResponse;
import xyz.cryptohows.backend.round.ui.dto.RoundSimpleResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static xyz.cryptohows.backend.acceptance.util.AcceptanceFixture.*;

@DisplayName("어드민 관련 기능")
class AdminAcceptanceTest extends AcceptanceTest {

    private static final AdminLoginRequest 어드민_로그인_양식 = new AdminLoginRequest("test", "test");

    private static final VentureCapitalRequest 벤처_캐피탈_요청_양식 = new VentureCapitalRequest("JOEL-VC", "joel's vc", "https://joel.vc", "https://joel.vc.img");

    private static final ProjectRequest 프로젝트_요청_양식 = new ProjectRequest("Joel-Project", "joel's project", "https://joel-project.com",
            "https://joel.image", "https://twitter-joel.com", "https://discord.joel",
            "DeFi", "ETHEREUM", "A16Z, 해시드, 조엘투자사");

    private static final RoundRequest 라운드_요청_양식 = new RoundRequest("ETHEREUM", "2022-03-13",
            "100000000", "https://news.com", "SERIES_D", "A16Z, 해시드, 조엘 투자사");

    @DisplayName("어드민 계정의 ID, PW를 통해 어드민 유저의 토큰을 발급받을 수 있다.")
    @Test
    void loginAsAdmin() {
        //when
        ExtractableResponse<Response> response = 어드민_로그인_요청(어드민_로그인_양식);

        //then
        어드민_로그인_요청_성공(response);
    }

    @DisplayName("올바르지 않은 ID, PW로는 어드민 유저의 토큰을 발급받을 수 없다.")
    @Test
    void cannotLoginAsAdmin() {
        //when
        ExtractableResponse<Response> response = 어드민_로그인_요청(new AdminLoginRequest("fail", "fail"));

        //then
        어드민_관련_요청_실패(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 VC 목록을 받아올 수 있다.")
    @Test
    void getVCAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_VC_전체_조회_요청(어드민_토큰_발급());

        //then
        어드민_VC_조회_응답_받음(response);
    }

    @DisplayName("어드민 토큰이 없다면, 어드민 권한으로 VC 목록을 받아올 수 없다.")
    @Test
    void cannotGetVCAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_VC_전체_조회_요청("failToken");

        //then
        어드민_관련_요청_실패(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 특정 VC 정보를 받아올 수 있다.")
    @Test
    void getSingleVCAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_VC_개별_조회_요청(어드민_토큰_발급(), 해시드.getId());

        // then
        어드민_VC_개별_응답_받음(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 VC를 삭제할 수 있다. " +
            "삭제 시 해당 VC가 유일한 파트너사인 프로젝트와 라운드 참여 주체 VC라면 프로젝트와 라운드도 각각 삭제된다.")
    @Test
    void deleteVCAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_VC_개별_삭제_요청(어드민_토큰_발급(), 해시드.getId());

        // then
        어드민_삭제_응답_받음(response);
        assertThat(ventureCapitalRepository.count()).isOne();
        assertThat(partnershipRepository.count()).isEqualTo(2);
        assertThat(roundParticipationRepository.count()).isEqualTo(4);
        assertThat(projectRepository.count()).isEqualTo(2);
        assertThat(roundRepository.count()).isEqualTo(4);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 프로젝트를 삭제할 수 있다.")
    @Test
    void deleteProjectAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_프로젝트_개별_삭제_요청(어드민_토큰_발급(), 이더리움.getId());

        // then
        어드민_삭제_응답_받음(response);
        assertThat(projectRepository.count()).isEqualTo(2);
        assertThat(partnershipRepository.count()).isEqualTo(3);
        assertThat(roundRepository.count()).isEqualTo(4);
        assertThat(roundParticipationRepository.count()).isEqualTo(5);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 라운드를 삭제할 수 있다.")
    @Test
    void deleteRoundAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_라운드_개별_삭제_요청(어드민_토큰_발급(), 이더리움_시드.getId());

        // then
        어드민_삭제_응답_받음(response);
        assertThat(roundRepository.count()).isEqualTo(5);
        assertThat(roundParticipationRepository.count()).isEqualTo(7);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 VC를 추가할 수 있다.")
    @Test
    void addVCAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_VC_개별_추가_요청(어드민_토큰_발급(), 벤처_캐피탈_요청_양식);

        // then
        어드민_추가_응답_받음(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 VC를 수정할 수 있다.")
    @Test
    void updateVCAsAdmin() {
        // when
        ExtractableResponse<Response> response = 어드민_VC_개별_수정_요청(어드민_토큰_발급(), 해시드.getId(), 벤처_캐피탈_요청_양식);

        // then
        어드민_성공_응답_받음(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 프로젝트를 등록할 수 있다.")
    @Test
    void createProjectAsAdmin() {
        // given
        ExtractableResponse<Response> response = 어드민_프로젝트_개별_추가_요청(어드민_토큰_발급(), 프로젝트_요청_양식);

        // then
        어드민_추가_응답_받음(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 프로젝트를 수정할 수 있다.")
    @Test
    void updateProjectAsAdmin() {
        // given
        ExtractableResponse<Response> response = 어드민_프로젝트_개별_수정_요청(어드민_토큰_발급(), 이더리움.getId(), 프로젝트_요청_양식);

        // then
        어드민_성공_응답_받음(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 라운드를 등록할 수 있다.")
    @Test
    void createRoundAsAdmin() {
        // given
        ExtractableResponse<Response> response = 어드민_라운드_개별_추가_요청(어드민_토큰_발급(), 라운드_요청_양식);

        // then
        어드민_추가_응답_받음(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 라운드를 수정할 수 있다.")
    @Test
    void updateRoundAsAdmin() {
        // given
        ExtractableResponse<Response> response = 어드민_라운드_개별_수정_요청(어드민_토큰_발급(), 이더리움_시드.getId(), 라운드_요청_양식);

        // then
        어드민_성공_응답_받음(response);
    }

    @DisplayName("어드민 로그인 후, 어드민 권한으로 라운드를 시간 순으로 받아올 수 있다.")
    @Test
    void getAdminRounds() {
        // given
        ExtractableResponse<Response> response = 어드민_라운드_전체_조회_요청(어드민_토큰_발급());

        // then
        어드민_라운드_조회_응답_받음(response);
    }

    private String 어드민_토큰_발급() {
        ExtractableResponse<Response> response = 어드민_로그인_요청(어드민_로그인_양식);
        TokenResponse adminLoginResponse = response.as(TokenResponse.class);
        return adminLoginResponse.getValue();
    }

    private ExtractableResponse<Response> 어드민_로그인_요청(AdminLoginRequest 어드민_로그인_양식) {
        return RestAssured.given().log().all()
                .body(어드민_로그인_양식)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/admin/login")
                .then().log().all()
                .extract();
    }

    private void 어드민_로그인_요청_성공(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        TokenResponse adminLoginResponse = response.as(TokenResponse.class);
        assertThat(adminLoginResponse).isNotNull();
    }

    private void 어드민_관련_요청_실패(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> 어드민_VC_전체_조회_요청(String 어드민_토큰) {
        return RestAssured.given().log().all()
                .when()
                .auth().oauth2(어드민_토큰)
                .get("/admin/venture-capitals")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_라운드_전체_조회_요청(String 어드민_토큰) {
        return RestAssured.given().log().all()
                .when()
                .auth().oauth2(어드민_토큰)
                .get("/admin/rounds")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_VC_개별_조회_요청(String 어드민_토큰, Long VC_ID) {
        return RestAssured.given().log().all()
                .when()
                .auth().oauth2(어드민_토큰)
                .get("/admin/venture-capitals/{vcId}", VC_ID)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_VC_개별_추가_요청(String 어드민_토큰, VentureCapitalRequest ventureCapitalRequest) {
        return RestAssured.given().log().all()
                .auth().oauth2(어드민_토큰)
                .body(ventureCapitalRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/admin/venture-capitals")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_프로젝트_개별_추가_요청(String 어드민_토큰, ProjectRequest projectRequest) {
        return RestAssured.given().log().all()
                .auth().oauth2(어드민_토큰)
                .body(projectRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/admin/projects")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_라운드_개별_추가_요청(String 어드민_토큰, RoundRequest roundRequest) {
        return RestAssured.given().log().all()
                .auth().oauth2(어드민_토큰)
                .body(roundRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/admin/rounds")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_VC_개별_수정_요청(String 어드민_토큰, Long VC_ID, VentureCapitalRequest ventureCapitalRequest) {
        return RestAssured.given().log().all()
                .auth().oauth2(어드민_토큰)
                .body(ventureCapitalRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/admin/venture-capitals/{vcId}", VC_ID)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_프로젝트_개별_수정_요청(String 어드민_토큰, Long PROJECT_ID, ProjectRequest projectRequest) {
        return RestAssured.given().log().all()
                .auth().oauth2(어드민_토큰)
                .body(projectRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/admin/projects/{projectId}", PROJECT_ID)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_라운드_개별_수정_요청(String 어드민_토큰, Long ROUND_ID, RoundRequest roundRequest) {
        return RestAssured.given().log().all()
                .auth().oauth2(어드민_토큰)
                .body(roundRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/admin/rounds/{roundId}", ROUND_ID)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_VC_개별_삭제_요청(String 어드민_토큰, Long VC_ID) {
        return RestAssured.given().log().all()
                .when()
                .auth().oauth2(어드민_토큰)
                .delete("/admin/venture-capitals/{vcId}", VC_ID)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_프로젝트_개별_삭제_요청(String 어드민_토큰, Long PROJECT_ID) {
        return RestAssured.given().log().all()
                .when()
                .auth().oauth2(어드민_토큰)
                .delete("/admin/projects/{projectId}", PROJECT_ID)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 어드민_라운드_개별_삭제_요청(String 어드민_토큰, Long ROUND_ID) {
        return RestAssured.given().log().all()
                .when()
                .auth().oauth2(어드민_토큰)
                .delete("/admin/rounds/{roundId}", ROUND_ID)
                .then().log().all()
                .extract();
    }

    private void 어드민_VC_조회_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        VentureCapitalSimpleResponse[] vcResponses = response.as(VentureCapitalSimpleResponse[].class);
        assertThat(vcResponses).isNotEmpty();
    }

    private void 어드민_라운드_조회_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        RoundSimpleResponse[] roundResponses = response.as(RoundSimpleResponse[].class);
        assertThat(roundResponses).isNotEmpty();
        List<String> responseAnnouncedDate = Arrays.stream(roundResponses)
                .map(RoundSimpleResponse::getAnnouncedDate)
                .collect(Collectors.toList());
        List<String> sortedAnnouncedDate = Arrays.stream(roundResponses)
                .map(RoundSimpleResponse::getAnnouncedDate)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        assertEquals(responseAnnouncedDate, sortedAnnouncedDate);
    }

    private void 어드민_VC_개별_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        VentureCapitalResponse vcResponse = response.as(VentureCapitalResponse.class);
        assertThat(vcResponse).isNotNull();
    }

    private void 어드민_추가_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private void 어드민_삭제_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 어드민_성공_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
