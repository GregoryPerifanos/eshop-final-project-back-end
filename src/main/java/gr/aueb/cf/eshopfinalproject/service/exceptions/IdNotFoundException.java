package gr.aueb.cf.eshopfinalproject.service.exceptions;

public class IdNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public IdNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }
}
