package xyz.cryptohows.backend.upload.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadService {

    private final VentureCapitalUploadService ventureCapitalUploadService;
    private final ProjectUploadService projectUploadService;
    private final RoundUploadService roundUploadService;

    public void uploadVentureCapitals(MultipartFile file) {
        ventureCapitalUploadService.uploadVentureCapitals(file);
    }

    public void uploadProjects(MultipartFile file) {
        projectUploadService.uploadProjects(file);
    }

    public void uploadRounds(MultipartFile file) {
        roundUploadService.uploadRounds(file);
    }
}
