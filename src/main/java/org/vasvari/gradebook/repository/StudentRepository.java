package org.vasvari.gradebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vasvari.gradebook.model.StudentPerson;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentPerson, Long> {

    Optional<StudentPerson> findByName(String name);

    void removeAllByName(String name);

    List<StudentPerson> findAllByName(String name);

}