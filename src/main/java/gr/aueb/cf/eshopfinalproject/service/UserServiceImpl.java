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
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the IUserService interface, providing services related to users.
 */
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

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the retrieved user as a UserDTO
     * @throws IdNotFoundException if no user with the specified ID is found
     */
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

    /**
     * Retrieves all users.
     *
     * @return a list of all users as UserDTOs
     */
    @Transactional
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users) {
            userDTOList.add(convertToUserDTO(user));
        }
        return userDTOList;
    }

    /**
     * Inserts a new user.
     *
     * @param userDTO the user to insert
     * @return the inserted user as a UserDTO
     * @throws UsernameAllReadyExists if the username already exists
     * @throws Exception if any other error occurs during the insertion
     */
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
        } catch (Exception e2) {
            throw new Exception("Error while inserting user: " + e2.getMessage());
        }
    }

    /**
     * Changes the password of a user.
     *
     * @param userName the username of the user
     * @param newPassword the new password
     * @return the updated user as a UserDTO
     * @throws Exception if any error occurs during the password change
     */
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
                user.setPassword(passwordEncoder.encode(newPassword));
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

    /**
     * Adds funds to a user's balance.
     *
     * @param userName the username of the user
     * @param newBalance the amount to add to the balance
     * @return the updated user as a UserDTO
     * @throws UsernameAllReadyExists if the username is not found
     */
    @Transactional
    @Override
    public UserDTO addFunds(String userName, Double newBalance) throws UsernameAllReadyExists {
        try {
            Optional<User> user = userRepository.findByUsername(userName);
            if (user.isPresent()) {
                User existingUser = user.get();
                existingUser.setBalance(existingUser.getBalance() + newBalance);
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

    /**
     * Logs in a user with the provided credentials.
     *
     * @param credentialsDTO the credentials of the user
     * @return the logged-in user as a UserDTO
     * @throws UsernameAllReadyExists if the username is not found
     * @throws PasswordNotFoundException if the password is incorrect
     */
    public UserDTO login(CredentialsDTO credentialsDTO) throws UsernameAllReadyExists, PasswordNotFoundException {
        User user = userRepository.findByUsername(credentialsDTO.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialsDTO.password(), user.getPassword())) {
            return convertToUserDTO(user);
        }
        throw new PasswordNotFoundException("Password not found");
    }

    /**
     * Converts a UserDTO to a User entity.
     *
     * @param userDTO the DTO to convert
     * @return the converted User entity
     */
    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setBalance(userDTO.getBalance());
        return user;
    }

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user the entity to convert
     * @return the converted UserDTO
     */
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
