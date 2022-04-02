package org.vasvari.gradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectOutput {
    private Long id;
    private String name;
    private SimpleTeacher teacher;
    private List<SimpleData> students;

}
