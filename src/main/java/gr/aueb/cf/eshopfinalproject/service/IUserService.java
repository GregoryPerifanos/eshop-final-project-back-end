package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {
    UserDTO getUserById(Long id) throws IdNotFoundException ;
    List<UserDTO> getAllUsers() throws IdNotFoundException, UsernameNotFoundException;
    UserDTO insertUser(UserDTO userDTO) throws UsernameAllReadyExists, Exception;
    UserDTO changePassword(Long id, String newPassword) throws IdNotFoundException;
    UserDTO addFunds(Long id, Long balance) throws IdNotFoundException;

}
