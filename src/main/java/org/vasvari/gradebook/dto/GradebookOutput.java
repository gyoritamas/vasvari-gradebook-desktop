package org.vasvari.gradebook.dto;

import lombok.*;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;
import org.vasvari.gradebook.dto.dataTypes.SimpleStudent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradebookOutput {
    private Long id;
    private SimpleStudent student;
    private SimpleData subject;
    private SimpleData assignment;
    private Integer grade;

}
