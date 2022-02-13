package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.Clazz;
import org.vasvari.gradebook.model.LessonEntry;
import org.vasvari.gradebook.model.Subject;
import org.vasvari.gradebook.model.TeacherPerson;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestLessonRepository {
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final TeacherPerson CREATED_BY = new TeacherPerson();
    private static final String COMMENT = "test_comment";
    private static final Subject SUBJECT = new Subject();
    private static final Clazz CLASS = new Clazz();
    private static final String TITLE = "test_title";
    private static final boolean SUBSTITUTION = false;

    private LessonEntry testLessonEntry;

    @Autowired
    private LessonRepository lessonRepo;

    @Before
    public void init() {
        testLessonEntry = new LessonEntry(
                CREATED_AT,
                CREATED_BY,
                COMMENT,
                SUBJECT,
                CLASS,
                TITLE,
                SUBSTITUTION
        );
    }

    @Test
    public void TestSaveClass() {
        lessonRepo.save(testLessonEntry);
        long savedId = testLessonEntry.getId();

        Optional<LessonEntry> foundLessonEntry = lessonRepo.findById(savedId);

        assertTrue(foundLessonEntry.isPresent());
        assertEquals(CREATED_AT, foundLessonEntry.get().getCreatedAt());
        assertEquals(CREATED_BY, foundLessonEntry.get().getCreatedBy());
        assertEquals(COMMENT, foundLessonEntry.get().getComment());
        assertEquals(SUBJECT, foundLessonEntry.get().getSubject());
        assertEquals(CLASS, foundLessonEntry.get().getClazz());
        assertEquals(TITLE, foundLessonEntry.get().getTitle());
        assertEquals(SUBSTITUTION, foundLessonEntry.get().getSubstitution());
    }

    @Test
    public void TestDeleteClass() {
        lessonRepo.save(testLessonEntry);
        long savedId = testLessonEntry.getId();
        lessonRepo.delete(testLessonEntry);

        Optional<LessonEntry> foundLessonEntry = lessonRepo.findById(savedId);

        assertFalse(foundLessonEntry.isPresent());
    }
}
