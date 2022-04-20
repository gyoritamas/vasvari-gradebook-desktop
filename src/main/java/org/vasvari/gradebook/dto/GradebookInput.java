package org.vasvari.gradebook.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GradebookInput {
    private Long studentId;
    private Long subjectId;
    private Long assignmentId;
    private Integer grade;

}