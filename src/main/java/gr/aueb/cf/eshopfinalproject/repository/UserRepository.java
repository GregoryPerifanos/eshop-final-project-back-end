package gr.aueb.cf.eshopfinalproject.repository;

import gr.aueb.cf.eshopfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
