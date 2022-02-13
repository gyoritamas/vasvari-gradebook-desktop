package org.vasvari.gradebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vasvari.gradebook.model.AttendanceEntry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntry, Long> {

    Optional<AttendanceEntry> findByCreatedAt(LocalDateTime afterThisTime);

}
