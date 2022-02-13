package org.vasvari.gradebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vasvari.gradebook.model.GradeEntry;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntry, Long> {

}
