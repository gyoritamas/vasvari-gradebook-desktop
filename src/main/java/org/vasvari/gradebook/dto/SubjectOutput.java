package org.vasvari.gradebook.dto;

import lombok.*;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;
import org.vasvari.gradebook.dto.dataTypes.SimpleStudent;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubjectOutput {
    private Long id;
    private String name;
    private SimpleTeacher teacher;
    private List<SimpleStudent> students;

    @Override
    public String toString() {
        return name;
    }
}
