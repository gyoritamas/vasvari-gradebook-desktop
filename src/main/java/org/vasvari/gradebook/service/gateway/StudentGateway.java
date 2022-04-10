package org.vasvari.gradebook.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.model.StudentOutputModel;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.util.TraversonUtil;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StudentGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public StudentDto findStudentById(Long id) {
        ResponseEntity<StudentOutputModel> response = template.getForEntity(baseUrl + "/students/{id}", StudentOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Failed to find student with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<StudentDto> findAllStudents() {
        String url = baseUrl + "/students";
        String linkTo = "self";

        return traversonUtil.getStudentDtoCollection(url, linkTo);
    }

    public Collection<StudentDto> searchStudents(StudentRequest request) {
        String url = baseUrl + "/students/search" + request.getFilter();
        String linkTo = "students-filtered";

        return traversonUtil.getStudentDtoCollection(url, linkTo);
    }

    public Collection<StudentDto> findStudentsOfCurrentUserAsTeacher() {
        String url = baseUrl + "/teacher-user/students";
        String linkTo = "students-of-teacher";

        return traversonUtil.getStudentDtoCollection(url, linkTo);
    }

    public Collection<StudentDto> findStudentsOfCurrentUserAsTeacher(StudentRequest request) {
        String url = baseUrl + "/teacher-user/students" + request.getFilter();
        String linkTo = "students-of-teacher";

        return traversonUtil.getStudentDtoCollection(url, linkTo);
    }

    public void saveStudent(StudentDto student) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/students", student, EntityModel.class);

        if (response.getStatusCodeValue() != 201) throw new RuntimeException("Failed to save student");
    }

    public void updateStudent(Long id, StudentDto update) {
        template.put(baseUrl + "/students/{id}", update, id);
    }

    public void deleteStudent(Long id) {
        template.delete(baseUrl + "/students/{id}", id);
    }

}
