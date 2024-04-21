package gr.aueb.cf.eshopfinalproject.service.exceptions;

public class PasswordNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public PasswordNotFoundException(String password) {
        super("Password not found: " + password);
    }
}
