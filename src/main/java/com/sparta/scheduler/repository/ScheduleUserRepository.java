package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.ScheduleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long> {
}
