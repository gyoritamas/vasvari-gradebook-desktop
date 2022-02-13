package org.vasvari.gradebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vasvari.gradebook.model.Clazz;

@Repository
public interface ClassRepository extends JpaRepository<Clazz, Long> {

}
