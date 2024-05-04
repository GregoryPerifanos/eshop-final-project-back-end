package gr.aueb.cf.eshopfinalproject;

import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.service.IUserService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EshopFinalProjectApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EshopFinalProjectApplication.class, args);
		System.out.println("you created the application");
	}



	}


