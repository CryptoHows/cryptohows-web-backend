package xyz.cryptohows.backend.vc.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;
import xyz.cryptohows.backend.vc.ui.dto.VentureCapitalSimpleResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VentureCapitalService {

    private final VentureCapitalRepository ventureCapitalRepository;

    public List<VentureCapitalSimpleResponse> findAllVentureCapitals() {
        List<VentureCapital> ventureCapitals = ventureCapitalRepository.findAll();
        return VentureCapitalSimpleResponse.toList(ventureCapitals);
    }
}
