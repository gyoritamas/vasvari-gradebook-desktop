package org.vasvari.gradebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class TeacherPerson extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(mappedBy = "teacherInCharge")
    private Clazz clazz;

    @OneToMany(mappedBy = "createdBy")
    private List<Entry> entries;

    public TeacherPerson(String name, LocalDate dateOfBirth, String email, String phone) {
        super(name, dateOfBirth, email, phone);
    }
}
