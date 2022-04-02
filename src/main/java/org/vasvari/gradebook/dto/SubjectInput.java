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

    @Size(min = 4, max = 20, message = "a méretnek 4 és 20 közötti értéknek kell lennie")
    @Pattern(regexp = "^[a-zA-Z]([0-9a-zA-Z]){3,20}",
            message = "a tantárgy neve csak betűket és számokat tartalmazhat és betűvel kell kezdődnie")
    private String name;

    @NotNull
    private Long teacherId;
}