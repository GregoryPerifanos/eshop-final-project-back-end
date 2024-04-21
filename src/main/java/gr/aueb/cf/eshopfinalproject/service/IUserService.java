package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameNotFoundException;

import java.util.List;

public interface IUserService {
    User getUserById(Long id) throws IdNotFoundException , UsernameNotFoundException;
    List<User> getAllUsers() throws IdNotFoundException, UsernameNotFoundException;
    User insertUser(User user) throws UsernameAllReadyExists;
    User changePassword(Long id, String newPassword) throws IdNotFoundException;
    User addFunds(Long balance) throws IdNotFoundException;

}
