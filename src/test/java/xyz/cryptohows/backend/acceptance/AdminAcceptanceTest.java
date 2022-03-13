package xyz.cryptohows.backend.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import xyz.cryptohows.backend.admin.ui.dto.AdminLoginRequest;
import xyz.cryptohows.backend.auth.ui.dto.TokenResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("어드민 관련 기능")
class AdminAcceptanceTest extends AcceptanceTest {

    private static final AdminLoginRequest 어드민_로그인_양식 = new AdminLoginRequest("test", "test");

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
        ExtractableResponse<Response> response = 어드민_VC_개별_조회_요청(어드민_토큰_발급(), 1L);

        // then
        어드민_VC_개별_응답_받음(response);
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

    private ExtractableResponse<Response> 어드민_VC_개별_조회_요청(String 어드민_토큰, Long VC_ID) {
        return RestAssured.given().log().all()
                .when()
                .auth().oauth2(어드민_토큰)
                .get("/admin/venture-capitals/{vcId}", VC_ID)
                .then().log().all()
                .extract();
    }

    private void 어드민_VC_조회_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        VentureCapitalSimpleResponse[] vcResponses = response.as(VentureCapitalSimpleResponse[].class);
        assertThat(vcResponses).isNotEmpty();
    }

    private void 어드민_VC_개별_응답_받음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        VentureCapitalResponse vcResponse = response.as(VentureCapitalResponse.class);
        assertThat(vcResponse).isNotNull();
    }
}
