package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.scheduleRepository = new ScheduleRepository(jdbcTemplate);
    }


    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    public ScheduleResponseDto getOneSchedule(Long id) {
        // DB 조회 (비밀번호 필드를 제외)
        return scheduleRepository.findOne(id);
    }

    public List<ScheduleResponseDto> getSchedules(LocalDate modifiedDate, String manager) {
        return scheduleRepository.findAll(modifiedDate, manager);

    }


    public ScheduleResponseDto updateSchedule(Long id, String password, ScheduleRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null && (password.equals(schedule.getPassword()))) {
            // memo 내용 수정
            schedule = scheduleRepository.update(id, requestDto, schedule);

            return new ScheduleResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id, String password) {

        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null && (password.equals(schedule.getPassword()))) {
            // schedule 삭제
            scheduleRepository.delete(id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


}
