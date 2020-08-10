package am.itspace.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('MALE','FEMALE')")
    private Gender gender;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('ADMIN','AUTHOR')")
    private Role role;
    private String bio;
    private String profilePic;
    private String email;
    private String password;
    private boolean active;
    private String token;
}
