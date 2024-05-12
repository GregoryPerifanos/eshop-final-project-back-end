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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDTO getUserById(Long id) throws IdNotFoundException {
        try {
          Optional<User> optionalUser = userRepository.findById(id);
          if (optionalUser.isPresent()) {
             User user = optionalUser.get();
             return convertToUserDTO(user);
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
    public List<UserDTO> getAllUsers() throws IdNotFoundException, UsernameNotFoundException {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users) {
            userDTOList.add(convertToUserDTO(user));
        }
        return userDTOList;
    }

    @Transactional
    @Override
    public UserDTO insertUser(UserDTO userDTO) throws UsernameAllReadyExists, Exception {
        try {
            User user = convertToUser(userDTO);
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new UsernameAllReadyExists("Username already exists: " + user.getUsername());
            }
            User insertedUser = userRepository.save(user);
            return convertToUserDTO(insertedUser);
        } catch (UsernameAllReadyExists e1) {
            throw e1;
        }
        catch (Exception e2) {
            throw new Exception("Error while inserting user: " + e2.getMessage());
        }
    }



    @Transactional
    @Override
    public UserDTO changePassword(Long id, String newPassword) throws IdNotFoundException {

        try {
            User user = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(User.class, id));
            user.setPassword(newPassword);
            userRepository.save(user);
            return convertToUserDTO(user);
        } catch (IdNotFoundException e) {
            log.info("Id not found");
            throw e;
        }
    }

    
    @Transactional
    @Override
    public UserDTO addFunds(Long id,Long balance) throws IdNotFoundException {
        User user = null;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(User.class, id));
            user.setBalance(user.getBalance() + balance);
            userRepository.save(user);
            return convertToUserDTO(user);
        } catch (IdNotFoundException e) {
            log.info("Id not found");
            throw e;
        }

    }

    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setBalance(userDTO.getBalance());
        return user;
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname(user.getFirstName());
        userDTO.setLastname(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setBalance(user.getBalance());
        return userDTO;
    }
}
