package org.vasvari.gradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.service.gateway.StudentGateway;
import org.vasvari.gradebook.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserUtil userUtil;
    private final StudentGateway gateway;

    public StudentDto findStudentById(Long id) {
        return gateway.findStudentById(id);
    }

    public List<StudentDto> findAllStudents() {
        return new ArrayList<>(gateway.findAllStudents());
    }

    public List<StudentDto> findStudentsForUser() {
        switch (userUtil.userRole()) {
            case ADMIN:
                return findAllStudents();
            case TEACHER:
                return findStudentsOfCurrentUserAsTeacher();
            case STUDENT:
//                return Collections.emptyList();
                throw new RuntimeException("Unauthorized");
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<StudentDto> findStudentsForUser(StudentRequest request) {
        switch (userUtil.userRole()) {
            case ADMIN:
                return searchStudents(request);
            case TEACHER:
                return findStudentsOfCurrentUserAsTeacher(request);
            case STUDENT:
//                return Collections.emptyList();
                throw new RuntimeException("Unauthorized");
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<StudentDto> searchStudents(StudentRequest request) {
        return new ArrayList<>(gateway.searchStudents(request));
    }

    private List<StudentDto> findStudentsOfCurrentUserAsTeacher() {
        return new ArrayList<>(gateway.findStudentsOfCurrentUserAsTeacher());
    }

    private List<StudentDto> findStudentsOfCurrentUserAsTeacher(StudentRequest request) {
        return new ArrayList<>(gateway.findStudentsOfCurrentUserAsTeacher(request));
    }

    public void saveStudent(StudentDto student) {
        gateway.saveStudent(student);
    }

    public void updateStudent(Long id, StudentDto student) {
        gateway.updateStudent(id, student);
    }

    public void deleteStudent(Long id) {
        gateway.deleteStudent(id);
    }
}
