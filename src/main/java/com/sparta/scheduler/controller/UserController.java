package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.UserRequestDto;
import com.sparta.scheduler.dto.UserResponseDto;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 5-1. 유저 저장
    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return userService.saveUser(requestDto);
    }

    // 5-1. 유저 단건 조회
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // 5-1. 유저 전체 조회
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // 5-1. 유저 수정
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    // 5-1. 유저 삭제
    @DeleteMapping("/{id}")
    public Long deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    // 5-3. 일정에 담당자 추가
    @PostMapping("/{id}/schedules/{scheduleId}/assign")
    public String assignUsersToSchedule(@PathVariable Long id, @PathVariable Long scheduleId, @RequestBody List<Long> managerUserList) {
        userService.assignUsersToSchedule(id, scheduleId, managerUserList);
        return "해당 유저들이 일정에 담당 유저로 저장되었습니다.";
    }


}
