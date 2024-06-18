package gr.aueb.cf.eshopfinalproject.service.exceptions;

import gr.aueb.cf.eshopfinalproject.model.User;

import java.io.Serial;

public class IdNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public IdNotFoundException(Class<?> entityClass, Long id) {
        super(entityClass.getSimpleName() + " with id " + id + " not found");
    }
}
