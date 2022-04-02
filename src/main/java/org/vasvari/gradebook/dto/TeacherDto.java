package org.vasvari.gradebook.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Relation(collectionRelation = "teachers", itemRelation = "teacher")
public class TeacherDto {
    private Long id;

    @Size(min = 2, message = "Firstname must be at least 2 characters long")
    private String firstname;

    @Size(min = 2, message = "Lastname must be at least 2 characters long")
    private String lastname;

    @NotBlank(message = "Email field cannot be empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Address field cannot be empty")
    private String address;

    @NotBlank(message = "Phone field cannot be empty")
    @Pattern(regexp = "^\\+?[\\d \\-()]{7,}",
            message = "Phone must be a valid phone number")
    private String phone;

    @NotNull(message = "A születési dátum nem lehet üres")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthdate;

    @JsonIgnore
    public String getName() {
        return lastname + " " + firstname;
    }

}
