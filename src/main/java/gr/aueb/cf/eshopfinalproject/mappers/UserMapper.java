package gr.aueb.cf.eshopfinalproject.mappers;

import gr.aueb.cf.eshopfinalproject.dto.SignUpDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDTO signUpDTO);
}
