package gr.aueb.cf.eshopfinalproject.controllers;

import gr.aueb.cf.eshopfinalproject.dto.AddFundsDTO;
import gr.aueb.cf.eshopfinalproject.dto.NewPasswordDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.IUserService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController handles user-related HTTP requests and responses.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserRepository userRepository;

    /**
     * Constructs a UserController with the specified IUserService and UserRepository.
     *
     * @param userService the user service to be used
     * @param userRepository the user repository to be used
     */
    public UserController(IUserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user.
     *
     * @param userDTO the user data transfer object containing the user's information
     * @return the created user
     */
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO insertedUser = userService.insertUser(userDTO);
            return ResponseEntity.ok(insertedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Changes the password of the authenticated user.
     *
     * @param newPasswordDTO the new password data transfer object
     * @param authentication the authentication object containing the user's authentication details
     * @return the updated user
     */
    @PostMapping("/password_change")
    public ResponseEntity<UserDTO> changePassword(@RequestBody NewPasswordDTO newPasswordDTO, Authentication authentication) {
        try {
            UserDTO userDTO = (UserDTO) authentication.getPrincipal();
            UserDTO updatedUserDTO = userService.changePassword(userDTO.getUsername(), newPasswordDTO.getNewPassword());
            return ResponseEntity.ok().body(updatedUserDTO);
        } catch (Exception e1) {
            e1.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Adds funds to the balance of the authenticated user.
     *
     * @param addFundsDTO the add funds data transfer object containing the amount to be added
     * @param authentication the authentication object containing the user's authentication details
     * @return the updated user
     */
    @PostMapping("/add_funds")
    public ResponseEntity<UserDTO> addFunds(@RequestBody AddFundsDTO addFundsDTO, Authentication authentication) {
        try {
            UserDTO userDTO = (UserDTO) authentication.getPrincipal();
            UserDTO updatedUserDTO = userService.addFunds(userDTO.getUsername(), addFundsDTO.getBalance());
            return ResponseEntity.ok().body(updatedUserDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return the user with the specified ID
     */
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        try {
            UserDTO user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (IdNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @GetMapping("/get_all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> userDTOs = userService.getAllUsers();
            return ResponseEntity.ok(userDTOs);
        } catch (UsernameNotFoundException | IdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
