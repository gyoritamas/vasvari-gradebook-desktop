package org.vasvari.gradebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "student")
public class StudentPerson extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne//(cascade=CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Clazz clazz;

    @OneToMany(mappedBy = "student")
    private List<AttendanceEntry> attendanceEntries;

    @OneToMany(mappedBy = "student")
    private List<GradeEntry> grades;

    public StudentPerson(String name, LocalDate dateOfBirth, String email, String phone) {
        super(name, dateOfBirth, email, phone);
    }
}
