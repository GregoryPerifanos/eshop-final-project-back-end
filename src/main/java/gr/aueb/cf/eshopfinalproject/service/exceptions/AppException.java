package gr.aueb.cf.eshopfinalproject.service.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class AppException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;
    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
