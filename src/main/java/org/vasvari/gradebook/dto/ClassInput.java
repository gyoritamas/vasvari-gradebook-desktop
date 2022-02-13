package org.vasvari.gradebook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ClassInput {

    @NotBlank(message = "Course field cannot be empty")
    @Schema(example = "Algebra")
    private String course;

}