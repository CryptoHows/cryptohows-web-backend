package xyz.cryptohows.backend.upload.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.upload.application.excel.VentureCapitalExcelFormat;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VentureCapitalUploadService {

    private final VentureCapitalRepository ventureCapitalRepository;

    public void uploadVentureCapitals(MultipartFile file) {
        List<VentureCapitalExcelFormat> ventureCapitalExcelFormats = VentureCapitalExcelFormat.toList(file);
        List<VentureCapital> uploadVentureCapitals = ventureCapitalExcelFormats.stream()
                .map(VentureCapitalExcelFormat::toVentureCapital)
                .collect(Collectors.toList());
        for (VentureCapital ventureCapital : uploadVentureCapitals) {
            checkExistenceAndUpload(ventureCapital);
        }
    }

    private void checkExistenceAndUpload(VentureCapital ventureCapital) {
        if (ventureCapitalRepository.existsByName(ventureCapital.getName())) {
            throw new CryptoHowsException(ventureCapital.getName() + "은 이미 업로드 되었거나, 파일 내 중복되어있는 벤처캐피탈 입니다.");
        }
        ventureCapitalRepository.save(ventureCapital);
    }
}
