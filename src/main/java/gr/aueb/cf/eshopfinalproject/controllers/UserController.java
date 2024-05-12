package gr.aueb.cf.eshopfinalproject.controllers;


import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.service.IUserService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


   @PostMapping("/create")
   public ResponseEntity<UserDTO> createUSer(@RequestBody UserDTO userDTO) {
       try {
         UserDTO insertedUser = userService.insertUser(userDTO);
         return ResponseEntity.ok(insertedUser);
       } catch (Exception e) {
           e.printStackTrace();
           return null;
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

   @GetMapping("/test")
    public ResponseEntity<String> test() {
       UserDTO userDTO = new UserDTO();
       userDTO.setUsername("Username");
       userDTO.setPassword("password");
       userDTO.setFirstname("John");
       userDTO.setLastname("Doe");
       userDTO.setEmail("john@doe.com");
       userDTO.setBalance(100L);

       try {
           userService.insertUser(userDTO);
           System.out.println("User inserted successfully.");
       } catch (Exception e) {
           System.out.println("Error inserting user: " + e.getMessage());
       }
       return ResponseEntity.ok("User inserted successfully.");
    }
}
