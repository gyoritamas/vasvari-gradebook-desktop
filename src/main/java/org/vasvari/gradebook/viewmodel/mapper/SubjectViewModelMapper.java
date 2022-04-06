package org.vasvari.gradebook.viewmodel.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.dto.TeacherDto;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;
import org.vasvari.gradebook.service.AssignmentService;
import org.vasvari.gradebook.viewmodel.SubjectViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubjectViewModelMapper {

    private final AssignmentService assignmentService;

    public SubjectViewModel map(SubjectOutput subjectOutput) {
        return SubjectViewModel.builder()
                .id(subjectOutput.getId())
                .name(subjectOutput.getName())
                .teacher(subjectOutput.getTeacher())
                .studentsCount(getNumberOfStudents(subjectOutput))
                .assignmentsCount(getNumberOfAssignments(subjectOutput))
                .build();
    }

    public List<SubjectViewModel> mapAll(List<SubjectOutput> subjectOutputList) {
        return subjectOutputList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private String getNumberOfStudents(SubjectOutput subjectOutput) {
        int numberOfStudents = subjectOutput.getStudents().size();
        return numberOfStudents == 0 ? "nincsenek tanulók" : (numberOfStudents + " fő");
    }

    private String getNumberOfAssignments(SubjectOutput subjectOutput) {
        Integer counter = assignmentService.mapAssignmentNumbersToSubjects().get(subjectOutput.getId());
        return counter == null ? "nincsenek feladatok" : (counter + " feladat");
    }
}
