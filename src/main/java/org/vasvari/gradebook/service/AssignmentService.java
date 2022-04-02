package org.vasvari.gradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.dto.AssignmentInput;
import org.vasvari.gradebook.dto.AssignmentOutput;
import org.vasvari.gradebook.model.request.AssignmentRequest;
import org.vasvari.gradebook.service.gateway.AssignmentGateway;
import org.vasvari.gradebook.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentGateway gateway;
    private final UserUtil userUtil;

    public AssignmentOutput findAssignmentById(Long id) {
        AssignmentOutput assignmentFound = gateway.findAssignmentById(id);
        if (!findAssignmentsForUserIncludeExpired().contains(assignmentFound))
            throw new RuntimeException("Unauthorized");

        return assignmentFound;
    }

    public List<AssignmentOutput> findAssignmentsForUser() {
        return findAssignmentsForUserIncludeExpired().stream()
                .filter(assignment -> assignment.isExpired().equals(false))
                .collect(Collectors.toList());
    }

    public List<AssignmentOutput> findAssignmentsForUserIncludeExpired() {
        switch (userUtil.userRole()) {
            case ADMIN:
                return findAllAssignments();
            case TEACHER:
                return findAssignmentsOfTeacherUser();
            case STUDENT:
                return findAssignmentsOfStudentUser();
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<AssignmentOutput> findAssignmentsForUser(AssignmentRequest request) {
        return findAssignmentsForUserIncludeExpired(request).stream()
                .filter(assignment -> assignment.isExpired().equals(false))
                .collect(Collectors.toList());
    }

    public List<AssignmentOutput> findAssignmentsForUserIncludeExpired(AssignmentRequest request) {
        switch (userUtil.userRole()) {
            case ADMIN:
                return searchAssignments(request);
            case TEACHER:
                return findAssignmentsOfTeacherUser(request);
            case STUDENT:
                return findAssignmentsOfStudentUser(request);
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<AssignmentOutput> findAllAssignments() {
        return new ArrayList<>(gateway.findAllAssignments());
    }

    public List<AssignmentOutput> searchAssignments(AssignmentRequest request) {
        return new ArrayList<>(gateway.searchAssignments(request));
    }

    public List<AssignmentOutput> findAssignmentsOfTeacherUser() {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsTeacher());
    }

    public List<AssignmentOutput> findAssignmentsOfTeacherUser(AssignmentRequest request) {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsTeacher(request));
    }

    public List<AssignmentOutput> findAssignmentsOfStudentUser() {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsStudent());
    }

    public List<AssignmentOutput> findAssignmentsOfStudentUser(AssignmentRequest request) {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsStudent(request));
    }

    public Map<Long, Integer> mapAssignmentNumbersToSubjects() {
        List<Long> subjectIds = findAssignmentsForUser().stream()
                .map(assignment -> assignment.getSubject().getId())
                .collect(Collectors.toList());
        Map<Long, Integer> map = subjectIds.stream().distinct()
                .collect(Collectors.toMap(subjectId -> subjectId, subjectId -> 0));
        for (Long subjectId : subjectIds) {
            Integer counter = map.get(subjectId);
            map.put(subjectId, counter + 1);
        }

        return map;
    }

    public void saveAssignment(AssignmentInput assignment) {
        gateway.save(assignment);
    }

    public void updateAssignment(Long id, AssignmentInput update) {
        gateway.updateAssignment(id, update);
    }

    public void deleteAssignment(Long id) {
        gateway.deleteAssignment(id);
    }
}
