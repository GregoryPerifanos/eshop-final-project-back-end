package gr.aueb.cf.eshopfinalproject.service.exceptions;

import java.io.Serial;

public class UsernameAllReadyExists extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAllReadyExists(String username) {

        super("Username " + username + " already exists");
    }
}
