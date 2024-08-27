package com.sparta.scheduler.controller;


import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    //JDBC DB 생성

    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 1. 일정 작성(Create)
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }



    // 2. 선택한 일정 조회 - id (Read)
    @GetMapping("/{id}")
    public ScheduleResponseDto getOneSchedule(@PathVariable Long id) {
        return scheduleService.getOneSchedule(id);

    }

    // 3-1. 일정 페이징 조회
    @GetMapping
    public Page<ScheduleResponseDto> getSchedules(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        return scheduleService.getSchedules(page, size);
    }


//    // 3. 일정 목록 조회 - modifiedTime, manager (Read)
//    @GetMapping("/schedules")
//    public List<ScheduleResponseDto> getSchedules(
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate modifiedDate,
//            @RequestParam(required = false) String manager) {
//
//        return scheduleService.getSchedules(modifiedDate, manager);
//
//    }

    // 4. 선택한 일정 수정 - writer, title, contents
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);

    }

    // 5. 선택한 일정 삭제 - id(Delete)
    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        return scheduleService.deleteSchedule(id);
    }

}
