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
@Table(name="lesson")
public class LessonEntry extends Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne//(cascade=CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne//(cascade=CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Clazz clazz;

    private String title;
    private Boolean substitution;

    public LessonEntry(LocalDateTime createdAt, TeacherPerson createdBy, String comment, Subject subject, Clazz clazz, String title, Boolean substitution) {
        super(createdAt, createdBy, comment);
        this.subject = subject;
        this.clazz = clazz;
        this.title = title;
        this.substitution = substitution;
    }
}
