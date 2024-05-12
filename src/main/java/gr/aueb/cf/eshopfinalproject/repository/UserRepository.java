package gr.aueb.cf.eshopfinalproject.repository;

import gr.aueb.cf.eshopfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
