package xyz.cryptohows.backend.admin.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.*;
import xyz.cryptohows.backend.admin.ui.dto.*;
import xyz.cryptohows.backend.admin.validation.AdminTokenRequired;
import xyz.cryptohows.backend.auth.ui.dto.TokenResponse;
import xyz.cryptohows.backend.project.ui.dto.CoinFullResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectResponse;
import xyz.cryptohows.backend.project.ui.dto.ProjectSimpleResponse;
import xyz.cryptohows.backend.round.ui.dto.RoundResponse;
import xyz.cryptohows.backend.round.ui.dto.RoundSimpleResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final VentureCapitalAdminService ventureCapitalAdminService;
    private final ProjectAdminService projectAdminService;
    private final RoundAdminService roundAdminService;
    private final CoinAdminService coinAdminService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginAsAdmin(@RequestBody AdminLoginRequest adminLoginRequest) {
        TokenResponse adminToken = adminService.login(adminLoginRequest);
        return ResponseEntity.ok(adminToken);
    }

    @AdminTokenRequired
    @GetMapping("/venture-capitals")
    public ResponseEntity<List<VentureCapitalSimpleResponse>> findAllVentureCapitals() {
        List<VentureCapitalSimpleResponse> ventureCapitalSimpleResponses = ventureCapitalAdminService.findAll();
        return ResponseEntity.ok(ventureCapitalSimpleResponses);
    }

    @AdminTokenRequired
    @PostMapping("/venture-capitals")
    public ResponseEntity<Void> createVentureCapital(@RequestBody VentureCapitalRequest ventureCapitalRequest) {
        ventureCapitalAdminService.create(ventureCapitalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @AdminTokenRequired
    @GetMapping("/venture-capitals/{vcId:[\\d]+}")
    public ResponseEntity<VentureCapitalResponse> findVentureCapital(@PathVariable Long vcId) {
        VentureCapitalResponse ventureCapitalResponse = ventureCapitalAdminService.findById(vcId);
        return ResponseEntity.ok(ventureCapitalResponse);
    }

    @AdminTokenRequired
    @PutMapping("/venture-capitals/{vcId:[\\d]+}")
    public ResponseEntity<Void> updateVentureCapital(@PathVariable Long vcId,
                                                     @RequestBody VentureCapitalRequest ventureCapitalRequest) {
        ventureCapitalAdminService.updateById(vcId, ventureCapitalRequest);
        return ResponseEntity.ok().build();
    }

    @AdminTokenRequired
    @DeleteMapping("/venture-capitals/{vcId:[\\d]+}")
    public ResponseEntity<Void> deleteVentureCapital(@PathVariable Long vcId) {
        ventureCapitalAdminService.deleteById(vcId);
        return ResponseEntity.noContent().build();
    }

    @AdminTokenRequired
    @PostMapping("/venture-capitals/upload-excel")
    public ResponseEntity<Void> uploadVentureCapitals(@RequestParam MultipartFile file) {
        ventureCapitalAdminService.uploadExcel(file);
        return ResponseEntity.ok().build();
    }

    @AdminTokenRequired
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectSimpleResponse>> findAllProjects() {
        List<ProjectSimpleResponse> projectSimpleResponses = projectAdminService.findAll();
        return ResponseEntity.ok(projectSimpleResponses);
    }

    @AdminTokenRequired
    @PostMapping("/projects")
    public ResponseEntity<Void> createProjects(@RequestBody ProjectRequest projectRequest) {
        projectAdminService.create(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @AdminTokenRequired
    @GetMapping("/projects/{projectId:[\\d]+}")
    public ResponseEntity<ProjectResponse> findProject(@PathVariable Long projectId) {
        ProjectResponse projectResponse = projectAdminService.findById(projectId);
        return ResponseEntity.ok(projectResponse);
    }

    @AdminTokenRequired
    @PutMapping("/projects/{projectId:[\\d]+}")
    public ResponseEntity<Void> updateProject(@PathVariable Long projectId,
                                              @RequestBody ProjectRequest projectRequest) {
        projectAdminService.updateById(projectId, projectRequest);
        return ResponseEntity.ok().build();
    }

    @AdminTokenRequired
    @DeleteMapping("/projects/{projectId:[\\d]+}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectAdminService.deleteById(projectId);
        return ResponseEntity.noContent().build();
    }

    @AdminTokenRequired
    @PostMapping("/projects/upload-excel")
    public ResponseEntity<Void> uploadProjects(@RequestParam MultipartFile file) {
        projectAdminService.uploadExcel(file);
        return ResponseEntity.ok().build();
    }

    @AdminTokenRequired
    @GetMapping("/rounds")
    public ResponseEntity<List<RoundSimpleResponse>> findAllRounds() {
        List<RoundSimpleResponse> roundSimpleResponses = roundAdminService.findAll();
        return ResponseEntity.ok(roundSimpleResponses);
    }

    @AdminTokenRequired
    @PostMapping("/rounds")
    public ResponseEntity<Void> createRounds(@RequestBody RoundRequest roundRequest) {
        roundAdminService.create(roundRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @AdminTokenRequired
    @GetMapping("/rounds/{roundId:[\\d]+}")
    public ResponseEntity<RoundResponse> findRound(@PathVariable Long roundId) {
        RoundResponse roundResponse = roundAdminService.findById(roundId);
        return ResponseEntity.ok(roundResponse);
    }

    @AdminTokenRequired
    @PutMapping("/rounds/{roundId:[\\d]+}")
    public ResponseEntity<Void> updateRound(@PathVariable Long roundId,
                                              @RequestBody RoundRequest roundRequest) {
        roundAdminService.updateById(roundId, roundRequest);
        return ResponseEntity.ok().build();
    }

    @AdminTokenRequired
    @DeleteMapping("/rounds/{roundId:[\\d]+}")
    public ResponseEntity<Void> deleteRound(@PathVariable Long roundId) {
        roundAdminService.deleteById(roundId);
        return ResponseEntity.noContent().build();
    }

    @AdminTokenRequired
    @PostMapping("/rounds/upload-excel")
    public ResponseEntity<Void> uploadRound(@RequestParam MultipartFile file) {
        roundAdminService.uploadExcel(file);
        return ResponseEntity.ok().build();
    }

    @AdminTokenRequired
    @GetMapping("/coins")
    public ResponseEntity<List<CoinFullResponse>> findAllCoins() {
        List<CoinFullResponse> roundSimpleResponses = coinAdminService.findAll();
        return ResponseEntity.ok(roundSimpleResponses);
    }

    @AdminTokenRequired
    @PostMapping("/coins")
    public ResponseEntity<Void> createCoin(@RequestBody CoinRequest coinRequest) {
        coinAdminService.create(coinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @AdminTokenRequired
    @PutMapping("/coins/{coinId:[\\d]+}")
    public ResponseEntity<Void> updateCoin(@PathVariable Long coinId,
                                            @RequestBody CoinRequest coinRequest) {
        coinAdminService.updateById(coinId, coinRequest);
        return ResponseEntity.ok().build();
    }

    @AdminTokenRequired
    @DeleteMapping("/coins/{coinId:[\\d]+}")
    public ResponseEntity<Void> deleteCoin(@PathVariable Long coinId) {
        coinAdminService.deleteById(coinId);
        return ResponseEntity.noContent().build();
    }

    @AdminTokenRequired
    @PostMapping("/coins/upload-excel")
    public ResponseEntity<Void> uploadCoin(@RequestParam MultipartFile file) {
        coinAdminService.uploadExcel(file);
        return ResponseEntity.ok().build();
    }
}
