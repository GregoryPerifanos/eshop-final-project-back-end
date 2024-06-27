package gr.aueb.cf.eshopfinalproject.service.exceptions;

import java.io.Serial;

public class EmailAllReadyExists extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAllReadyExists(String email) {
        super("Email: " + email + " is already in use");
    }
}
