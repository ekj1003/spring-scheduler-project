package com.sparta.scheduler.controller;


import com.sparta.scheduler.dto.OneScheduleResponseDto;
import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    //JDBC DB 생성

    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 1. 일정 작성(Create)
    // 5-2. 일정 작성자 아이디 받아서, 유저 고유 식별자 필드를 가지도록.
    @PostMapping("/{userId}")
    public ScheduleResponseDto createSchedule(@PathVariable Long userId, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(userId, requestDto);
    }



    // 2. 선택한 일정 조회 - id (Read)
    @GetMapping("/{id}")
    public OneScheduleResponseDto getOneSchedule(@PathVariable Long id) { // // 6-1. 일정 단건 조회 시 담당 유저들의 고유 식별자, 유저명, 이메일이 추가로 포함
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
