package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id);
//    List<Schedule> findByWriter(String writer);
//    List<Schedule> findByTitle(String Title);
//    List<Schedule> findByModifiedDateBetween(LocalDateTime start, LocalDateTime end);
//    List<Schedule> findByManagerAndModifiedDateBetween(String manager, LocalDateTime start, LocalDateTime end);
}