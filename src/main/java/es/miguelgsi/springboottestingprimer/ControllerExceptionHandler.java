package es.miguelgsi.springboottestingprimer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(RuntimeException e) {
        return ResponseEntity.internalServerError()
                .body(e.getMessage());
    }
}
