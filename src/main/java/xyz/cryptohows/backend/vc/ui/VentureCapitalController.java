package xyz.cryptohows.backend.vc.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.vc.application.VentureCapitalService;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VentureCapitalController {

    private final VentureCapitalService ventureCapitalService;

    @GetMapping("/vc")
    public ResponseEntity<List<VentureCapitalSimpleResponse>> findAllVentureCapitals() {
        List<VentureCapitalSimpleResponse> ventureCapitalSimpleRespons = ventureCapitalService.findAllVentureCapitals();
        return ResponseEntity.ok(ventureCapitalSimpleRespons);
    }
}
