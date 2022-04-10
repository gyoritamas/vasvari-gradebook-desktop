package org.vasvari.gradebook.dto.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.GradebookInput;
import org.vasvari.gradebook.dto.GradebookOutput;

@Component
public class GradebookEntryMapper {
    public GradebookInput map(GradebookOutput gradebookOutput) {
        return GradebookInput.builder()
                .studentId(gradebookOutput.getStudent().getId())
                .subjectId(gradebookOutput.getSubject().getId())
                .assignmentId(gradebookOutput.getAssignment().getId())
                .grade(gradebookOutput.getGrade())
                .build();
    }
}
