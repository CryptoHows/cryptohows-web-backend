package xyz.cryptohows.backend.vc.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.vc.application.VentureCapitalService;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalResponse;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VentureCapitalController {

    private final VentureCapitalService ventureCapitalService;

    @GetMapping("/venture-capitals")
    public ResponseEntity<List<VentureCapitalSimpleResponse>> findAllVentureCapitals() {
        List<VentureCapitalSimpleResponse> ventureCapitalSimpleResponses = ventureCapitalService.findAllVentureCapitals();
        return ResponseEntity.ok(ventureCapitalSimpleResponses);
    }

    @GetMapping("/venture-capitals/{vcId:[\\d]+}")
    public ResponseEntity<VentureCapitalResponse> findVentureCapital(@PathVariable Long vcId) {
        VentureCapitalResponse ventureCapitalResponse = ventureCapitalService.findVentureCapitalByName(vcId);
        return ResponseEntity.ok(ventureCapitalResponse);
    }

    @GetMapping("/venture-capitals/names")
    public ResponseEntity<List<String>> findVentureCapitalNames() {
        List<String> ventureCapitalNames = ventureCapitalService.findAllVentureCapitalNames();
        return ResponseEntity.ok(ventureCapitalNames);
    }
}
