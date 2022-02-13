package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.Attendance;
import org.vasvari.gradebook.model.AttendanceEntry;
import org.vasvari.gradebook.model.StudentPerson;
import org.vasvari.gradebook.model.TeacherPerson;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestAttendanceRepository {
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final TeacherPerson CREATED_BY = new TeacherPerson();
    private static final String COMMENT = "test_comment";
    private static final byte NUMBER_OF_LESSON = 2;
    private static final StudentPerson STUDENT = new StudentPerson();
    private static final Attendance ATTENDANCE = Attendance.ABSENT;
    private static final boolean EXCUSED = false;

    private AttendanceEntry testEntry;

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Before
    public void init() {
        testEntry = new AttendanceEntry(
                CREATED_AT,
                CREATED_BY,
                COMMENT,
                NUMBER_OF_LESSON,
                STUDENT,
                ATTENDANCE,
                EXCUSED
        );
    }

    @Test
    public void TestSaveAttendanceEntry() {
        attendanceRepo.save(testEntry);
        long savedId = testEntry.getId();

        Optional<AttendanceEntry> foundEntry = attendanceRepo.findById(savedId);

        assertTrue(foundEntry.isPresent());
        assertEquals(CREATED_AT, foundEntry.get().getCreatedAt());
        assertEquals(CREATED_BY, foundEntry.get().getCreatedBy());
        assertEquals(COMMENT, foundEntry.get().getComment());
        assertEquals(NUMBER_OF_LESSON, foundEntry.get().getNumberOfLesson());
        assertEquals(STUDENT, foundEntry.get().getStudent());
        assertEquals(ATTENDANCE, foundEntry.get().getAttendance());
        assertEquals(EXCUSED, foundEntry.get().getExcused());
    }

    @Test
    public void TestDeleteAttendanceEntry() {
        attendanceRepo.save(testEntry);
        long savedId = testEntry.getId();
        attendanceRepo.delete(testEntry);

        Optional<AttendanceEntry> foundEntry = attendanceRepo.findById(savedId);

        assertFalse(foundEntry.isPresent());
    }
}
