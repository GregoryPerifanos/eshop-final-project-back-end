package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    User getUserById(Long id) throws IdNotFoundException ;
    List<User> getAllUsers() throws IdNotFoundException, UsernameNotFoundException;
    User insertUser(UserDTO userDTO) throws UsernameAllReadyExists, Exception;
    User changePassword(Long id, String newPassword) throws IdNotFoundException;
    User addFunds(Long id, Long balance) throws IdNotFoundException;

}
