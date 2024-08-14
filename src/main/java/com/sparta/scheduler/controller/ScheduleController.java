package com.sparta.scheduler.controller;


import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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



    // 2. 선택한 일정 조회 - id (Read)
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


    // 3. 일정 목록 조회 - modifiedTime, manager (Read)
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedule(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate modifiedDate,
            @RequestParam(required = false) String manager) {

        // 기본 쿼리
        StringBuilder sql = new StringBuilder("SELECT id, contents, manager, createTime, modifiedTime FROM schedule WHERE 1=1");

        List<Object> params = new ArrayList<>();

        // 수정일만 들어왔을 때
        if (modifiedDate != null && manager == null) {
            sql.append(" AND DATE(modifiedTime) = ?");
            params.add(modifiedDate);
        }

        // 담당자만 들어왔을 때
        else if(modifiedDate == null && manager != null) {
            sql.append(" AND manager = ?");
            params.add(manager);
        }

        // 수정일, 담당자 모두 들어왔을 때
        else if (modifiedDate != null && manager != null) {
            sql.append(" AND DATE(modifiedTime) = ?");
            params.add(modifiedDate);
            sql.append(" AND manager = ?");
            params.add(manager);
        }


        // 정렬 수정일 기준 내림차순
        sql.append(" ORDER BY modifiedTime DESC");

        // 쿼리 실행
        return jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String contents = rs.getString("contents");
                String manager = rs.getString("manager");
                LocalDateTime createTime = rs.getTimestamp("createTime").toLocalDateTime();
                LocalDateTime modifiedTime = rs.getTimestamp("modifiedTime").toLocalDateTime();
                return new ScheduleResponseDto(id, contents, manager, createTime, modifiedTime);
            }
        });
    }

    // 4. 선택한 일정 수정 - contents, manager만 수정 가능(Update)
    @PutMapping("/schedules/{id}/{password}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @PathVariable String password, @RequestBody ScheduleRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if(schedule != null && (password.equals(schedule.getPassword()))) {
            // memo 내용 수정
            String sql = "UPDATE schedule SET contents = ?, manager = ?, modifiedTime = ? WHERE id = ?";
            LocalDateTime now = LocalDateTime.now(); // 수정한 시간 현재 시간으로 설정
            jdbcTemplate.update(sql, requestDto.getContents(), requestDto.getManager(), Timestamp.valueOf(now), id);

            schedule.setId(id);
            schedule.setContents(requestDto.getContents());
            schedule.setManager(requestDto.getManager());
            schedule.setModifiedTime(now);


            return new ScheduleResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }





    private Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setContents(resultSet.getString("contents"));
                schedule.setManager(resultSet.getString("manager"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setCreateTime(resultSet.getTimestamp("createTime").toLocalDateTime());
                schedule.setModifiedTime(resultSet.getTimestamp("modifiedTime").toLocalDateTime());
                return schedule;
            } else {
                return null;
            }
        }, id);
    }





}
