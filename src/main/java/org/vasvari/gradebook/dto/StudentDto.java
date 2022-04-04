package org.vasvari.gradebook.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentDto {
    private Long id;

    @Size(min = 2, message = "A keresztnév legalább 2 karakter hosszú kell legyen")
    private String firstname;

    @Size(min = 2, message = "A vezetéknév legalább 2 karakter hosszú kell legyen")
    private String lastname;

    @NotNull(message = "Az évfolyam nem lehet üres")
    @Min(value = 1, message = "Az évfolyam 1-12 érték kell legyen")
    @Max(value = 12, message = "Az évfolyam 1-12 érték kell legyen")
    private Integer gradeLevel;

    @NotBlank(message = "Az email mező nem lehet üres")
    @Email(message = "Az email formátuma hibás")
    private String email;

    @NotBlank(message = "A cím mező nem lehet üres")
    private String address;

    @NotBlank(message = "A telefonszám nem lehet üres")
    @Pattern(regexp = "^\\+?[\\d \\-()]{7,}",
            message = "A telefonszám formátuma hibás")
    private String phone;

    @NotNull(message = "A születési dátum nem lehet üres")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthdate;

    public String getName() {
        return lastname + " " + firstname;
    }
}
