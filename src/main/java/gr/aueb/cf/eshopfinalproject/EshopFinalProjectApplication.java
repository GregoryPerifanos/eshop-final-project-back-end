package gr.aueb.cf.eshopfinalproject;


import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.IUserService;
import gr.aueb.cf.eshopfinalproject.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class EshopFinalProjectApplication {

	private static IUserService userService;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EshopFinalProjectApplication.class, args);
		System.out.println("you created the application");

	}



}


