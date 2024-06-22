package gr.aueb.cf.eshopfinalproject.controllers;

import gr.aueb.cf.eshopfinalproject.config.UserAuthProvider;
import gr.aueb.cf.eshopfinalproject.dto.CredentialsDTO;
import gr.aueb.cf.eshopfinalproject.dto.SignUpDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.service.UserServiceImpl;
import gr.aueb.cf.eshopfinalproject.service.exceptions.PasswordNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDTO) throws PasswordNotFoundException, UsernameAllReadyExists {
        UserDTO userDTO = userService.login(credentialsDTO);
        userDTO.setPassword(userAuthProvider.createToken(userDTO));
        return ResponseEntity.ok(userDTO);
    }

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
