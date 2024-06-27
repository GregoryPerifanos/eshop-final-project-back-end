package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.CredentialsDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.mappers.UserMapper;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public UserDTO getUserById(Long id) throws IdNotFoundException {
        try {
          Optional<User> optionalUser = userRepository.findById(id);
          if (optionalUser.isPresent()) {
             User user = optionalUser.get();
             return convertToUserDTO(user);
          } else {
              throw new IdNotFoundException(User.class, id);
          }
        } catch (IdNotFoundException e1) {
            log.info("User id not found");
            throw e1;
        }
    }

    @Transactional
    @Override
    public List<UserDTO> getAllUsers() throws IdNotFoundException, UsernameNotFoundException {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users) {
            userDTOList.add(convertToUserDTO(user));
        }
        return userDTOList;
    }

    @Transactional
    @Override
    public UserDTO insertUser(UserDTO userDTO) throws UsernameAllReadyExists, Exception {
        try {
            User user = convertToUser(userDTO);
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new UsernameAllReadyExists("Username already exists: " + user.getUsername());
            }
            User insertedUser = userRepository.save(user);
            return convertToUserDTO(insertedUser);
        } catch (UsernameAllReadyExists e1) {
            throw e1;
        }
        catch (Exception e2) {
            throw new Exception("Error while inserting user: " + e2.getMessage());
        }
    }



    @Transactional
    @Override
    public UserDTO changePassword(String userName, String newPassword) throws Exception {
        try {
            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty.");
            }
            Optional<User> optionalUser = userRepository.findByUsername(userName);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setPassword(newPassword);
                userRepository.save(user);
                return convertToUserDTO(user);
            } else {
                throw new UsernameNotFoundException("Username not found: " + userName);
            }
        } catch (Exception e) {
            log.info("Change of Password with that username has failed", e);
            throw e;
        }
    }

    
    @Transactional
    @Override
    public UserDTO addFunds(String userName, Double newBalance) throws UsernameAllReadyExists {
        try {
            Optional<User> user = userRepository.findByUsername(userName);
            if (user.isPresent()) {
                User existingUser = user.get();
                existingUser.setBalance(user.get().getBalance() + newBalance);
                userRepository.save(existingUser);
                return convertToUserDTO(existingUser);
            } else {
                throw new UsernameAllReadyExists("Username not found: " + userName);
            }
        } catch (UsernameAllReadyExists e) {
            log.info("Update of Funds with that username has failed");
            throw e;
        }

    }

    public UserDTO login (CredentialsDTO credentialsDTO) throws UsernameAllReadyExists, PasswordNotFoundException {
        User user = userRepository.findByUsername(credentialsDTO.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (user.getPassword().equals(credentialsDTO.password())) {
            return convertToUserDTO(user);
        }
        throw new PasswordNotFoundException("Password not found");
    }


    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setBalance(userDTO.getBalance());
        return user;
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstName());
        userDTO.setLastname(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setBalance(user.getBalance());
        return userDTO;
    }
}
