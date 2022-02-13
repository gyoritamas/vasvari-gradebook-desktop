package org.vasvari.gradebook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    protected Long id;
    protected LocalDateTime createdAt;

    @ManyToOne//(cascade=CascadeType.MERGE)
    @JoinColumn(name = "created_by")
    protected TeacherPerson createdBy;

    protected String comment;

    public Entry(LocalDateTime createdAt, TeacherPerson createdBy, String comment) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.comment = comment;
    }
}
