package gr.aueb.cf.eshopfinalproject.service.exceptions;

import java.io.Serial;

public class PasswordNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public PasswordNotFoundException(String password) {
        super("Password not found: " + password);
    }
}
