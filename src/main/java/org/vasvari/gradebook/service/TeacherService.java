package org.vasvari.gradebook.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.exception.PersonNotFoundException;
import org.vasvari.gradebook.helpers.EmailValidator;
import org.vasvari.gradebook.helpers.RegistrationRequest;
import org.vasvari.gradebook.model.TeacherPerson;
import org.vasvari.gradebook.repository.TeacherRepository;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@AllArgsConstructor
public class TeacherService {
    private final String ID_NOT_FOUND_ERROR = "Cannot find student with id %d";
    private final String NAME_NOT_FOUND_ERROR = "Cannot find student with id %s";
    private final EmailValidator emailValidator;
    private final TeacherRepository teacherRepo;

//    @Autowired
//    public TeacherService(TeacherRepository teacherRepo) {
//        this.teacherRepo = teacherRepo;
//    }
//
//    @Autowired
//    public TeacherService(TeacherRepository teacherRepo, EmailValidator emailValidator) {
//        this.teacherRepo = teacherRepo;
//        this.emailValidator = emailValidator;
//    }

    public TeacherPerson addTeacher(TeacherPerson teacher) {
        requireNonNull(teacher.getName());
        requireNonNull(teacher.getEmail());
        testEmail(teacher);
        requireNonNull(teacher.getDateOfBirth());

        return teacherRepo.save(teacher);
    }

    public List<TeacherPerson> findAllTeachers() {
        return teacherRepo.findAll();
    }

    public TeacherPerson updateTeacher(TeacherPerson teacher) {
        return teacherRepo.save(teacher);
    }

    public TeacherPerson findTeacherById(Long id) {
        return teacherRepo.findById(id).orElseThrow(() -> new PersonNotFoundException(String.format(ID_NOT_FOUND_ERROR, id)));
    }

    public void deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
    }

    public TeacherPerson findTeacherByName(String name) {
        return teacherRepo.findByName(name)
                .orElseThrow(() -> new PersonNotFoundException(String.format(NAME_NOT_FOUND_ERROR, name)));
    }

    private void testEmail(TeacherPerson teacher) {
        boolean isValidEmail = emailValidator.test(teacher.getEmail());

        if (!isValidEmail)
            throw new IllegalStateException("email is not valid");
    }
}
