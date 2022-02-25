package xyz.cryptohows.backend.upload.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.upload.application.UploadService;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @Value("${upload.password}")
    private String password;

    @PostMapping("/upload/venture-capitals")
    public ResponseEntity<Void> uploadVentureCapitals(@RequestParam MultipartFile file,
                                                      @RequestParam String password) {
        validatePassword(password);
        uploadService.uploadVentureCapitals(file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload/projects")
    public ResponseEntity<Void> uploadProjects(@RequestParam MultipartFile file,
                                               @RequestParam String password) {
        validatePassword(password);
        uploadService.uploadProjects(file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload/rounds")
    public ResponseEntity<Void> uploadRounds(@RequestParam MultipartFile file,
                                             @RequestParam String password) {
        validatePassword(password);
        uploadService.uploadRounds(file);
        return ResponseEntity.ok().build();
    }

    private void validatePassword(String password) {
        if (this.password.equals(password)) {
            return;
        }
        throw new CryptoHowsException("패스워드가 일치하지 않습니다.");
    }
}
