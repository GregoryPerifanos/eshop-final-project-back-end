package gr.aueb.cf.eshopfinalproject.service.exceptions;

public class UsernameAllReadyExists extends Exception {

    private static final long serialVersionUID = 1L;

    public UsernameAllReadyExists(String username) {

        super("Username " + username + " already exists");
    }
}
