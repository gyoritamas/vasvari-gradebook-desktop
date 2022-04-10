package org.vasvari.gradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.dto.SubjectInput;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.request.SubjectRequest;
import org.vasvari.gradebook.service.gateway.SubjectGateway;
import org.vasvari.gradebook.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectGateway gateway;
    private final UserUtil userUtil;

    public SubjectOutput findSubjectById(Long id) {
        return gateway.findSubjectById(id);
    }

    public List<SubjectOutput> findSubjectsForUser() {
        switch (userUtil.userRole()) {
            case ADMIN:
                return findAllSubjects();
            case TEACHER:
                return findSubjectsOfTeacherUser();
            case STUDENT:
                return findSubjectsOfStudentUser();
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<SubjectOutput> findSubjectsForUser(SubjectRequest request) {
        switch (userUtil.userRole()) {
            case ADMIN:
                return searchSubjects(request);
            case TEACHER:
                return findSubjectsOfTeacherUser(request);
            case STUDENT:
                return findSubjectsOfStudentUser(request);
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<SubjectOutput> findAllSubjects() {
        return new ArrayList<>(gateway.findAllSubjects());
    }

    public List<SubjectOutput> searchSubjects(SubjectRequest request) {
        return new ArrayList<>(gateway.searchSubjects(request));
    }

    private List<SubjectOutput> findSubjectsOfTeacherUser() {
        return new ArrayList<>(gateway.findSubjectsOfCurrentUserAsTeacher());
    }

    private List<SubjectOutput> findSubjectsOfTeacherUser(SubjectRequest request) {
        return new ArrayList<>(gateway.findSubjectsOfCurrentUserAsTeacher(request));
    }

    private List<SubjectOutput> findSubjectsOfStudentUser() {
        return new ArrayList<>(gateway.findSubjectsOfCurrentUserAsStudent());
    }

    private List<SubjectOutput> findSubjectsOfStudentUser(SubjectRequest request) {
        return new ArrayList<>(gateway.findSubjectsOfCurrentUserAsStudent(request));
    }

    public List<StudentDto> findStudentsOfSubject(Long subjectId) {
        return new ArrayList<>(gateway.findStudentsOfSubject(subjectId));
    }

    public void saveSubject(SubjectInput subjectInput) {
        gateway.saveSubject(subjectInput);
    }

    public void updateSubject(Long id, SubjectInput update) {
        gateway.updateSubject(id, update);
    }

    public void addStudentToSubject(Long subjectId, Long studentId) {
        gateway.addStudentToSubject(subjectId, studentId);
    }

    public void removeStudentFromSubject(Long subjectId, Long studentId) {
        gateway.removeStudentFromSubject(subjectId, studentId);
    }

    public void deleteSubject(Long id) {
        gateway.deleteSubject(id);
    }
}
