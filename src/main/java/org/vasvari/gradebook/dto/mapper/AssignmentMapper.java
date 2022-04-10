package org.vasvari.gradebook.dto.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.AssignmentInput;
import org.vasvari.gradebook.dto.AssignmentOutput;

@Component
public class AssignmentMapper {
    public AssignmentInput map(AssignmentOutput assignmentOutput) {
        return AssignmentInput.builder()
                .name(assignmentOutput.getName())
                .type(assignmentOutput.getType())
                .deadline(assignmentOutput.getDeadline())
                .description(assignmentOutput.getDescription())
                .subjectId(assignmentOutput.getSubject().getId())
                .build();
    }
}
