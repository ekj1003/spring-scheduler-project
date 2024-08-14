package com.sparta.scheduler.controller;


import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    //JDBC DB 생성

    private final JdbcTemplate jdbcTemplate;
    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. 일정 작성(Create)
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
                    // LocalDateTime 형 jdbc에 저장하는 방법
                    // LocalDateTime -> Timestamp
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



    // 2. 선택한 일정 조회(Read)
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getMemos(@PathVariable Long id) {
        // DB 조회 (비밀번호 필드를 제외)
        String sql = "SELECT id, contents, manager, createTime, modifiedTime FROM schedule WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long scheduleId = rs.getLong("id");
                String contents = rs.getString("contents");
                String manager = rs.getString("manager");
                LocalDateTime createTime = rs.getTimestamp("createTime").toLocalDateTime();
                LocalDateTime modifiedTime = rs.getTimestamp("modifiedTime").toLocalDateTime();
                return new ScheduleResponseDto(scheduleId, contents, manager, createTime, modifiedTime);
            }
        });
    }

//
//    private Schedule findById(Long id) {
//        // DB 조회
//        String sql = "SELECT * FROM schedule WHERE id = ?";
//
//        return jdbcTemplate.query(sql, resultSet -> {
//            if(resultSet.next()) {
//                Schedule schedule = new Schedule();
//                schedule.setUsername(resultSet.getString("username"));
//                memo.setContents(resultSet.getString("contents"));
//                return memo;
//            } else {
//                return null;
//            }
//        }, id);
//    }





}
