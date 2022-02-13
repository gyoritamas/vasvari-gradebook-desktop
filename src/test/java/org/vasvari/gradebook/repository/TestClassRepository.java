package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.Clazz;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestClassRepository {
    private static final short START_YEAR = 2019;
    private static final byte START_NUMBER = 13;
    private static final byte NUMBER_OF_YEARS = 2;
    private static final String CLASS_LETTER = "de";

    private Clazz testClass;

    @Autowired
    private ClassRepository classRepo;

    @Before
    public void init() {
        testClass = new Clazz(
                START_YEAR,
                START_NUMBER,
                NUMBER_OF_YEARS,
                CLASS_LETTER
        );
    }

    @Test
    public void TestSaveClass() {
        classRepo.save(testClass);
        long savedId = testClass.getId();

        Optional<Clazz> foundClass = classRepo.findById(savedId);

        assertTrue(foundClass.isPresent());
        assertEquals(START_YEAR, foundClass.get().getStartYear());
        assertEquals(START_NUMBER, foundClass.get().getStartNumber());
        assertEquals(NUMBER_OF_YEARS, foundClass.get().getNumberOfYears());
        assertEquals(CLASS_LETTER, foundClass.get().getClassLetter());
    }

    @Test
    public void TestDeleteClass() {
        classRepo.save(testClass);
        long savedId = testClass.getId();
        classRepo.delete(testClass);

        Optional<Clazz> foundClass = classRepo.findById(savedId);

        assertFalse(foundClass.isPresent());
    }
}
