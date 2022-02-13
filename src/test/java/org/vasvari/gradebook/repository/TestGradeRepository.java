package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.GradeEntry;
import org.vasvari.gradebook.model.StudentPerson;
import org.vasvari.gradebook.model.Subject;
import org.vasvari.gradebook.model.TeacherPerson;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestGradeRepository {
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final TeacherPerson CREATED_BY = new TeacherPerson();
    private static final String COMMENT = "test_comment";
    private static final StudentPerson STUDENT = new StudentPerson();
    private static final Subject SUBJECT = new Subject();
    private static final String TITLE = "test_grade_entry";
    private static final byte GRADE = 4;

    private GradeEntry testGradeEntry;

    @Autowired
    private GradeRepository gradeRepo;

    @Before
    public void init() {
        testGradeEntry = new GradeEntry(
                CREATED_AT,
                CREATED_BY,
                COMMENT,
                STUDENT,
                SUBJECT,
                TITLE,
                GRADE
        );
    }

    @Test
    public void TestSaveGradeEntry() {
        gradeRepo.save(testGradeEntry);
        long id = testGradeEntry.getId();

        Optional<GradeEntry> foundGradeEntry = gradeRepo.findById(id);

        assertTrue(foundGradeEntry.isPresent());
        assertEquals(CREATED_AT, foundGradeEntry.get().getCreatedAt());
        assertEquals(CREATED_BY, foundGradeEntry.get().getCreatedBy());
        assertEquals(COMMENT, foundGradeEntry.get().getComment());
        assertEquals(STUDENT, foundGradeEntry.get().getStudent());
        assertEquals(SUBJECT, foundGradeEntry.get().getSubject());
        assertEquals(TITLE, foundGradeEntry.get().getTitle());
        assertEquals(GRADE, foundGradeEntry.get().getGrade());
    }

    @Test
    public void TestDeleteGradeEntry() {
        gradeRepo.save(testGradeEntry);
        long id = testGradeEntry.getId();
        gradeRepo.delete(testGradeEntry);

        Optional<GradeEntry> foundGradeEntry = gradeRepo.findById(id);

        assertFalse(foundGradeEntry.isPresent());
    }
}
