package xyz.cryptohows.backend.upload.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import xyz.cryptohows.backend.upload.application.UploadService;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;


}
