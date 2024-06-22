package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.CredentialsDTO;
import gr.aueb.cf.eshopfinalproject.dto.SignUpDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.mappers.UserMapper;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
import gr.aueb.cf.eshopfinalproject.service.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
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
            log.info("User id not found");
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
            log.info("Change of Password with that id has failed");
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
            log.info("Update of Funds with that id has failed");
            throw e;
        }

    }

    public UserDTO login (CredentialsDTO credentialsDTO) throws UsernameAllReadyExists, PasswordNotFoundException {
        User user = userRepository.findByUsername(credentialsDTO.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

//        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getPassword())) {
//            return userMapper.toUserDTO(user);
//        }
        if (user.getPassword().equals(credentialsDTO.password())) {
            return convertToUserDTO(user);
        }
        throw new PasswordNotFoundException("Password not found");
    }

    public UserDTO register (SignUpDTO signUpDTO) throws UsernameAllReadyExists, PasswordNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(signUpDTO.email());

        if (optionalUser.isPresent()) {
            throw new UsernameAllReadyExists("Username already exists: " + HttpStatus.BAD_REQUEST);
        }


        User user = userMapper.signUpToUser(signUpDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
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
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstName());
        userDTO.setLastname(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setBalance(user.getBalance());
        return userDTO;
    }
}
