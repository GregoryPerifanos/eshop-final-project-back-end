package gr.aueb.cf.eshopfinalproject.config;

import gr.aueb.cf.eshopfinalproject.dto.ErrorDTO;
import gr.aueb.cf.eshopfinalproject.service.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleException(AppException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorDTO(e.getMessage()));
    }
}
