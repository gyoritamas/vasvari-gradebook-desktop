package org.vasvari.gradebook.dto;

import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Relation(collectionRelation = "students", itemRelation = "student")
@EqualsAndHashCode
public class StudentDto {

    private Long id;

    private String firstname;

    private String lastname;

    private Integer gradeLevel;

    private String email;

    private String address;

    private String phone;

    private String birthdate;

    public String getName() {
        return firstname + " " + lastname;
    }

    public StudentDto(String firstname, String lastname, Integer gradeLevel, String email, String address, String phone, String birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gradeLevel = gradeLevel;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.birthdate = birthdate;
    }
}
