package org.vasvari.gradebook.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class StudentDto {
    private Long id;
    private String firstname;
    private String lastname;
    private Integer gradeLevel;
    private String email;
    private String address;
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthdate;

    public String getName() {
        return lastname + " " + firstname;
    }

    @Override
    public String toString() {
        return getName();
    }
}
