package org.vasvari.gradebook.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.dto.SubjectInput;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.SubjectOutputModel;
import org.vasvari.gradebook.model.request.SubjectRequest;
import org.vasvari.gradebook.util.TraversonUtil;

import java.util.Collection;

@Service
@ComponentScan
@RequiredArgsConstructor
public class SubjectGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public SubjectOutput findSubjectById(Long id) {
        ResponseEntity<SubjectOutputModel> response = template.getForEntity(baseUrl + "/subjects/{id}", SubjectOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            // TODO: use custom exception
            throw new RuntimeException("Failed to find subject with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<SubjectOutput> findAllSubjects() {
        String url = baseUrl + "/subjects";
        String linkTo = "self";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<SubjectOutput> searchSubjects(SubjectRequest request) {
        String url = baseUrl + "/subjects/search" + request.getFilter();
        String linkTo = "subjects-filtered";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<SubjectOutput> findSubjectsOfCurrentUserAsTeacher() {
        String url = baseUrl + "/teacher-user/subjects";
        String linkTo = "subjects-of-teacher";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<SubjectOutput> findSubjectsOfCurrentUserAsTeacher(SubjectRequest request) {
        String url = baseUrl + "/teacher-user/subjects" + request.getFilter();
        String linkTo = "subjects-of-teacher";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<SubjectOutput> findSubjectsOfCurrentUserAsStudent() {
        String url = baseUrl + "/student-user/subjects";
        String linkTo = "subjects-of-student";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<SubjectOutput> findSubjectsOfCurrentUserAsStudent(SubjectRequest request) {
        String url = baseUrl + "/student-user/subjects" + request.getFilter();
        String linkTo = "subjects-of-student";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<StudentDto> findStudentsOfSubject(Long subjectId) {
        String url = String.format("%s/subjects/%s/students", baseUrl, subjectId);
        String linkTo = "students-of-subject";

        return traversonUtil.getStudentDtoCollection(url, linkTo);
    }

    public void saveSubject(SubjectInput subject) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/subjects", subject, EntityModel.class);
        // TODO: use custom exception
        if (response.getStatusCodeValue() != 201 || response.getBody() == null)
            throw new RuntimeException("Failed to save subject");
    }

    public void updateSubject(Long id, SubjectInput update) {
        template.put(baseUrl + "/subjects/{id}", update, id);
    }

    public void addStudentToSubject(Long subjectId, Long studentId) {
        ResponseEntity<?> response = template.postForEntity(
                String.format("%s/subjects/%d/add_student/%d", baseUrl, subjectId, studentId),
                null,
                EntityModel.class);

        if (response.getStatusCodeValue() != 200)
            throw new RuntimeException(String.format("Failed to add student %d to subject %d", studentId, subjectId));
    }

    public void removeStudentFromSubject(Long subjectId, Long studentId) {
        ResponseEntity<?> response = template.postForEntity(
                String.format("%s/subjects/%d/remove_student/%d", baseUrl, subjectId, studentId),
                null,
                EntityModel.class);

        if (response.getStatusCodeValue() != 200)
            throw new RuntimeException(String.format("Failed to add student %d to subject %d", studentId, subjectId));
    }

    public void deleteSubject(Long id) {
        template.delete(baseUrl + "/subjects/{id}", id);
    }
}