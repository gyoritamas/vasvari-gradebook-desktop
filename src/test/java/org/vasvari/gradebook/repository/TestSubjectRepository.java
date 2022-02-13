package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.Subject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestSubjectRepository {
    private static final String TEST_SUBJECT_NAME = "test_subject";

    private Subject testSubject;

    @Before
    public void init() {
        testSubject = new Subject(TEST_SUBJECT_NAME);
    }

    @Autowired
    private SubjectRepository subjectRepo;

    @Test
    public void TestSaveSubject() {
        subjectRepo.save(testSubject);

        Optional<Subject> foundSubject = subjectRepo.findByName(TEST_SUBJECT_NAME);

        assertTrue(foundSubject.isPresent());
        assertEquals(TEST_SUBJECT_NAME, foundSubject.get().getName());
    }

    @Test
    public void TestDeleteSubject() {
        subjectRepo.save(testSubject);
        subjectRepo.delete(testSubject);

        Optional<Subject> foundSubject = subjectRepo.findByName(TEST_SUBJECT_NAME);

        assertFalse(foundSubject.isPresent());
    }

}