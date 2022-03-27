package xyz.cryptohows.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(CryptoHowsException.class)
    public ResponseEntity<String> handleCryptoHowsException(CryptoHowsException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> handleDomainException(DomainException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e.getMessage());
        for (String message : Arrays.toString(e.getStackTrace()).split(",")) {
            stringBuilder.append(message).append("\n");
        }
        log.error(stringBuilder.toString());
        return ResponseEntity.internalServerError().build();
    }
}
