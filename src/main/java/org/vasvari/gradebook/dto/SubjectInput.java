package org.vasvari.gradebook.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubjectInput {
    private Long id;
    private String name;
    private Long teacherId;
}