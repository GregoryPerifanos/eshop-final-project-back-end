package gr.aueb.cf.eshopfinalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO extends BaseDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private Long balance;
}
