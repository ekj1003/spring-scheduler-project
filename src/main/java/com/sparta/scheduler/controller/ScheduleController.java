package com.sparta.scheduler.controller;


import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    //JDBC DB 생성

    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 1. 일정 작성(Create)
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }



    // 2. 선택한 일정 조회 - id (Read)
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getOneSchedule(@PathVariable Long id) {
        return scheduleService.getOneSchedule(id);

    }


    // 3. 일정 목록 조회 - modifiedTime, manager (Read)
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate modifiedDate,
            @RequestParam(required = false) String manager) {

        return scheduleService.getSchedules(modifiedDate, manager);

    }

    // 4. 선택한 일정 수정 - contents, manager만 수정 가능(Update)
    @PutMapping("/schedules/{id}/{password}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @PathVariable String password, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, password,requestDto);

    }

    // 5. 선택한 일정 삭제 - id와 password (Delete)
    @DeleteMapping("/schedules/{id}/{password}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String password) {
        return scheduleService.deleteSchedule(id, password);
    }

}
