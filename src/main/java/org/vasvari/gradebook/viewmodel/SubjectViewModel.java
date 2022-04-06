package org.vasvari.gradebook.viewmodel;

import lombok.*;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectViewModel {
    private Long id;
    private String name;
    private SimpleTeacher teacher;
    private String studentsCount;
    private String assignmentsCount;
}
