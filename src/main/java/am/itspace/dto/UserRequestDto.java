package am.itspace.dto;


import am.itspace.model.Gender;
import am.itspace.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    private int id;
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Surname is Required")
    private String surname;
    private Gender gender;
    private Role role;
    @NotBlank(message = "Bio is Required")
    private String bio;
    @Email(message = "Email is not valid")
    private String email;
    @Size(min = 6,message = "Password length should be at leaset 6 symbol")
    private String password;
    @Size(min = 6,message = "Confim  password length should be at leaset 6 symbol")
    private String confirmPassword;
}
