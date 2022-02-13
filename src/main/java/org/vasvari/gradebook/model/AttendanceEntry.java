package org.vasvari.gradebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "attendance")
public class AttendanceEntry extends Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private Byte numberOfLesson;

    @ManyToOne//(cascade=CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentPerson student;

    @Enumerated(EnumType.STRING)
    private Attendance attendance;
    private Boolean excused;

    public AttendanceEntry(LocalDateTime createdAt, TeacherPerson createdBy, String comment, Byte numberOfLesson, StudentPerson student, Attendance attendance, Boolean excused) {
        super(createdAt, createdBy, comment);
        this.numberOfLesson = numberOfLesson;
        this.student = student;
        this.attendance = attendance;
        this.excused = excused;
    }
}


