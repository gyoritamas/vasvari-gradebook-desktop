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

    private String name;
    private AssignmentType type;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline = LocalDate.now().plusDays(1);

    private Long subjectId;

}
