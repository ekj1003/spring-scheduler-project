package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// ScheduleRepository.java
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id);
    List<Schedule> findByModifiedDateBetween(LocalDateTime start, LocalDateTime end);
    List<Schedule> findByManager(String manager);
    List<Schedule> findByManagerAndModifiedDateBetween(String manager, LocalDateTime start, LocalDateTime end);
}