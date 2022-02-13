package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.TeacherPerson;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestTeacherRepository {
    private static final String TEST_TEACHER_NAME = "test_teacher";
    private static final LocalDate TEST_TEACHER_DOB = LocalDate.of(1970, 1, 1);
    private static final String TEST_TEACHER_EMAIL = "test_teacher@test.org";
    private static final String TEST_TEACHER_PHONE = "123456789";

    private TeacherPerson testTeacher;

    @Autowired
    private TeacherRepository teacherRepo;

    @Before
    public void init() {
        testTeacher = new TeacherPerson(
                TEST_TEACHER_NAME,
                TEST_TEACHER_DOB,
                TEST_TEACHER_EMAIL,
                TEST_TEACHER_PHONE
        );
    }

    @Test
    public void TestAddTeacher() {
        teacherRepo.save(testTeacher);

        Optional<TeacherPerson> foundTeacher = teacherRepo.findByName(TEST_TEACHER_NAME);

        assertTrue(foundTeacher.isPresent());
        assertEquals(TEST_TEACHER_NAME, foundTeacher.get().getName());
        assertEquals(TEST_TEACHER_DOB, foundTeacher.get().getDateOfBirth());
        assertEquals(TEST_TEACHER_EMAIL, foundTeacher.get().getEmail());
        assertEquals(TEST_TEACHER_PHONE, foundTeacher.get().getPhone());
    }

    @Test
    public void TestRemoveTeacher() {
        teacherRepo.save(testTeacher);
        teacherRepo.delete(testTeacher);

        Optional<TeacherPerson> foundTeacher = teacherRepo.findByName(TEST_TEACHER_NAME);

        assertFalse(foundTeacher.isPresent());
    }
}
