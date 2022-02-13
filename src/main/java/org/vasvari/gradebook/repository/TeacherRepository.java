package org.vasvari.gradebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vasvari.gradebook.model.TeacherPerson;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherPerson, Long> {

    Optional<TeacherPerson> findByName(String name);

    void removeAllByName(String testTeacherName);

    List<TeacherPerson> findAllByName(String testTeacherName);
}
