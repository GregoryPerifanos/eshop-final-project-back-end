package gr.aueb.cf.eshopfinalproject.service.exceptions;

public class UsernameNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("Username " + username + " not found");
    }
}
