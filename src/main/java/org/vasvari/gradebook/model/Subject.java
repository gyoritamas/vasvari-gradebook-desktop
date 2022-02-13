package org.vasvari.gradebook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<LessonEntry> lessons;

    @OneToMany(mappedBy = "subject")
    private List<GradeEntry> grades;

    public Subject(String name) {
        this.name = name;
    }
}
