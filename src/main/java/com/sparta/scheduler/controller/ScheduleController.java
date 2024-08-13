package com.sparta.scheduler.controller;


import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    //JDBC DB 생성

    private JdbcTemplate jdbcTemplate = null;
    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Create
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체
        String sql = "INSERT INTO schedule (contents, manager, password, createTime, modifiedTime) VALUES (?, ?, ?, ?, ?)"; // 값은 동적이므로 ?, ?
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getContents());
                    preparedStatement.setString(2, schedule.getManager());
                    preparedStatement.setString(3, schedule.getPassword());
                    // LocalDataTime 형 jdbc에 저장하는 방법
                    // LocalDataTime -> Timestamp
                    // Timestamp.valueOf(LocalDateTime 형) 사용
                    preparedStatement.setTimestamp(4, Timestamp.valueOf(schedule.getCreateTime()));
                    preparedStatement.setTimestamp(5, Timestamp.valueOf(schedule.getModifiedTime()));

                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

}
