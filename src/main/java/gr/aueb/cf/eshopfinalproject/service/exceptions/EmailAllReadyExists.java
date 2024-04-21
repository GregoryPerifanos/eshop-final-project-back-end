package gr.aueb.cf.eshopfinalproject.service.exceptions;

public class EmailAllReadyExists extends Exception {
    private static final long serialVersionUID = 1L;

    public EmailAllReadyExists(String email) {
        super("Email: " + email + " is already in use");
    }
}
