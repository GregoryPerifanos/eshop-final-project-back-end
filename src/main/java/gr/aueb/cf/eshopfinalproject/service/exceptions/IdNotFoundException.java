package gr.aueb.cf.eshopfinalproject.service.exceptions;

import gr.aueb.cf.eshopfinalproject.model.User;

public class IdNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public IdNotFoundException(Class<User> userClass, Long id) {
        super("User with id " + id + " not found");
    }
}
