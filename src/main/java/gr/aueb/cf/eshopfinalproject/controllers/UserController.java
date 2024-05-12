package gr.aueb.cf.eshopfinalproject.controllers;


import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.IUserService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

   public UserController(@Qualifier("IUserService") IUserService userService) {
       this.userService = userService;
   }


   @RequestMapping("/create")
   public User createUSer(@RequestBody UserDTO userDTO) {
       try {
           return userService.insertUser(userDTO);
       }catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }

   @RequestMapping("/change")
   public User getUserById(@PathVariable Long userId) {
       try {
           return userService.getUserById(userId);
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }

   }

}
