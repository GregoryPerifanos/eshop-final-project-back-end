package gr.aueb.cf.eshopfinalproject.controllers;


import gr.aueb.cf.eshopfinalproject.dto.AddFundsDTO;
import gr.aueb.cf.eshopfinalproject.dto.NewPasswordDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.IUserService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserRepository userRepository;

    public UserController(IUserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


   @PostMapping("/create")
   public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
       try {
         UserDTO insertedUser = userService.insertUser(userDTO);
         return ResponseEntity.ok(insertedUser);
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }

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

   @GetMapping("/get_all")
   public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
           List<UserDTO> userDTOs= userService.getAllUsers();
            return ResponseEntity.ok(userDTOs);
        } catch (UsernameNotFoundException | IdNotFoundException e){
            return ResponseEntity.notFound().build();
        }
   }
}
