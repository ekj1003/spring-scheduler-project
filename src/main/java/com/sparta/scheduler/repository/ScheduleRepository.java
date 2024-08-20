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
    default List<Schedule> findByModifiedDateAndManager(LocalDate modifiedDate, String manager) {
        LocalDateTime start = modifiedDate.atStartOfDay();  // 시작 시간
        LocalDateTime end = start.plusDays(1).minusNanos(1);  // 끝 시간
        List<Schedule> schedules = findByModifiedDateBetween(start, end);
        return schedules.stream()
                .filter(schedule -> manager.equals(schedule.getManager()))
                .collect(Collectors.toList());
    }
}