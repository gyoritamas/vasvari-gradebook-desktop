package org.vasvari.gradebook.dto.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.SubjectInput;
import org.vasvari.gradebook.dto.SubjectOutput;

@Component
public class SubjectMapper {
    public SubjectInput map(SubjectOutput subjectOutput) {
        return SubjectInput.builder()
                .id(subjectOutput.getId())
                .name(subjectOutput.getName())
                .teacherId(subjectOutput.getTeacher().getId())
                .build();
    }
}
