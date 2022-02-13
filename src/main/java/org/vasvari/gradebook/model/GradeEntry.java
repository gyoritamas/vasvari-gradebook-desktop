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
@Table(name = "grade")
public class GradeEntry extends Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne//(cascade=CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentPerson student;

    @ManyToOne//(cascade=CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String title;
    private Byte grade;

    public GradeEntry(LocalDateTime createdAt, TeacherPerson createdBy, String comment, StudentPerson student, Subject subject, String title, Byte grade) {
        super(createdAt, createdBy, comment);
        this.student = student;
        this.subject = subject;
        this.title = title;
        this.grade = grade;
    }
}
