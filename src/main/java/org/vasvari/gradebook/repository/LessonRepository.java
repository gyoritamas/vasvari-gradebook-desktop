package org.vasvari.gradebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vasvari.gradebook.model.LessonEntry;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntry, Long> {
}
