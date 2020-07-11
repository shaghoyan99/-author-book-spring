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
@Table(name = "author")


public class Author {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('MALE','FEMALE')")
    private Gender gender;
    @Column
    private String bio;
}
