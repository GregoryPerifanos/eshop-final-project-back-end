package gr.aueb.cf.eshopfinalproject.controllers;

import gr.aueb.cf.eshopfinalproject.config.UserAuthProvider;
import gr.aueb.cf.eshopfinalproject.dto.CredentialsDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.service.UserServiceImpl;
import gr.aueb.cf.eshopfinalproject.service.exceptions.PasswordNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController handles authentication-related HTTP requests.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final UserAuthProvider userAuthProvider;

    /**
     * Handles user login.
     *
     * @param credentialsDTO the credentials data transfer object containing username and password
     * @return the authenticated user with a generated token
     * @throws PasswordNotFoundException if the password is not found
     * @throws UsernameAllReadyExists if the username already exists
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDTO) throws PasswordNotFoundException, UsernameAllReadyExists {
        UserDTO userDTO = userService.login(credentialsDTO);
        userDTO.setPassword(userAuthProvider.createToken(userDTO));
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Handles user registration.
     *
     * @param userDTO the user data transfer object containing user details
     * @return the registered user with a generated token
     * @throws PasswordNotFoundException if the password is not found
     * @throws UsernameAllReadyExists if the username already exists
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws PasswordNotFoundException, UsernameAllReadyExists {
        try {
            UserDTO insertedUser = userService.insertUser(userDTO);
            userDTO.setPassword(userAuthProvider.createToken(userDTO));
            return ResponseEntity.ok(insertedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
