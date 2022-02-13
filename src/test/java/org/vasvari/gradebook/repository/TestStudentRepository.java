package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.StudentPerson;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestStudentRepository {
    private static final String TEST_STUDENT_NAME = "test_student";
    private static final LocalDate TEST_STUDENT_DOB = LocalDate.of(2000, 1, 1);
    private static final String TEST_STUDENT_EMAIL = "test_student@test.org";
    private static final String TEST_STUDENT_PHONE = "123456789";

    private StudentPerson testStudent;

    @Autowired
    private StudentRepository studentRepo;

    @Before
    public void init() {
        testStudent = new StudentPerson(
                TEST_STUDENT_NAME,
                TEST_STUDENT_DOB,
                TEST_STUDENT_EMAIL,
                TEST_STUDENT_PHONE
        );
    }

    @Test
    public void TestSaveStudent() {
        studentRepo.save(testStudent);

        Optional<StudentPerson> addedStudent = studentRepo.findByName(TEST_STUDENT_NAME);

        assertTrue(addedStudent.isPresent());
        assertEquals(TEST_STUDENT_NAME, addedStudent.get().getName());
        assertEquals(TEST_STUDENT_DOB, addedStudent.get().getDateOfBirth());
        assertEquals(TEST_STUDENT_EMAIL, addedStudent.get().getEmail());
        assertEquals(TEST_STUDENT_PHONE, addedStudent.get().getPhone());
    }

    @Test
    public void TestDeleteStudent() {
        studentRepo.save(testStudent);
        studentRepo.delete(testStudent);

        Optional<StudentPerson> foundStudent = studentRepo.findByName(TEST_STUDENT_NAME);

        assertFalse(foundStudent.isPresent());
    }

}
