package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getUserById(Long id) throws IdNotFoundException {
        User user;
        try {
            user = userRepository.findById(id).get();
            if (user == null) throw new IdNotFoundException(User.class, id);
        } catch (IdNotFoundException e1) {
            log.info("Id not found");
            throw e1;
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws IdNotFoundException, UsernameNotFoundException {
        return List.of();
    }

    @Transactional
    @Override
    public User insertUser(User user) throws UsernameAllReadyExists, Exception {
        User user1 = null;
        try {
            user1 = userRepository.save(user);
            if (user1.getId() == null) throw new Exception("Insert Error");
            if (user1 == user) throw new UsernameAllReadyExists("User already exists");

        }catch(Exception e) {
            log.info("Exception occurred while inserting user");
        }
        return user1;
    }

    @Transactional
    @Override
    public User changePassword(Long id, String newPassword) throws IdNotFoundException {
        User user = null;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(User.class, id));
            user.setPassword(newPassword);
            userRepository.save(user);
        } catch (IdNotFoundException e) {
            log.info("Id not found");
        }
        return user;
    }

    @Override
    public User addFunds(Long id,Long balance) throws IdNotFoundException {
        User user = null;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(User.class, id));
            user.setBalance(balance);
            userRepository.save(user);
        }catch(IdNotFoundException e) {
            log.info("Id not found");
        }
        return user;
    }
}
