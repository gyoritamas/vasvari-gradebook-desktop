package org.vasvari.gradebook.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "class")
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Short startYear;
    private Byte startNumber;
    private Byte numberOfYears;
    private String classLetter;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private TeacherPerson teacherInCharge;

    @OneToMany(mappedBy = "clazz")
    private List<StudentPerson> students;

    @OneToMany(mappedBy = "clazz")
    private List<LessonEntry> lessons;

    public Clazz(Short startYear, Byte startNumber, Byte numberOfYears, String classLetter) {
        this.startYear = startYear;
        this.startNumber = startNumber;
        this.numberOfYears = numberOfYears;
        this.classLetter = classLetter;
    }
}
