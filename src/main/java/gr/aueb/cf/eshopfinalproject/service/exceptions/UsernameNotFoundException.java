package gr.aueb.cf.eshopfinalproject.service.exceptions;

import java.io.Serial;

public class UsernameNotFoundException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("Username " + username + " not found");
    }
}
