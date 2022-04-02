package org.vasvari.gradebook.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentInput {

    @NotBlank(message = "Name field cannot be empty")
    private String name;

    @NotNull
    private AssignmentType type;

    private String description;

    @NotNull(message = "Deadline field cannot be empty")
    @Future(message = "Deadline must be a date in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline = LocalDate.now().plusDays(1);

    @NotNull(message = "Subject ID cannot be empty")
    private Long subjectId;

}
