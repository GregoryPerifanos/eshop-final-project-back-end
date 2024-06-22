package gr.aueb.cf.eshopfinalproject.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO extends BaseDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private Long balance;
}
