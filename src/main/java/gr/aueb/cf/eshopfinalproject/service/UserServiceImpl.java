package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameAllReadyExists;
import gr.aueb.cf.eshopfinalproject.service.exceptions.UsernameNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User getUserById(Long id) throws IdNotFoundException {
        try {
          Optional<User> user = userRepository.findById(id);
          if (user.isPresent()) {
              return user.get();
          } else {
              throw new IdNotFoundException(User.class, id);
          }
        } catch (IdNotFoundException e1) {
            log.info("Id not found");
            throw e1;
        }
    }

    @Transactional
    @Override
    public List<User> getAllUsers() throws IdNotFoundException, UsernameNotFoundException {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User insertUser(UserDTO userDTO) throws UsernameAllReadyExists, Exception {
        try {
            User user = ConvertToUser(userDTO);
            if (userRepository.existsById(user.getId())) {
                throw new UsernameAllReadyExists("Username already exists: " + user.getId());
            }
            userRepository.save(user);
            return user;
        } catch (UsernameAllReadyExists e1) {
            throw e1;
        }
        catch (Exception e2) {
            throw new Exception("Error while inserting user: " + e2.getMessage());
        }
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

    
    @Transactional
    @Override
    public User addFunds(Long id,Long balance) throws IdNotFoundException {
        User user = null;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(User.class, id));
            user.setBalance(user.getBalance() + balance);
            userRepository.save(user);
        } catch (IdNotFoundException e) {
            log.info("Id not found");
        }
        return user;
    }

    private static User ConvertToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
