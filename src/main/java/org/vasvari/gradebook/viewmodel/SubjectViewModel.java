package org.vasvari.gradebook.viewmodel;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectViewModel {
    private Long id;
    private String name;
    private String teacherName;
    private String studentsCount;
    private String assignmentsCount;
}
