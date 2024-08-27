package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.UserRequestDto;
import com.sparta.scheduler.dto.UserResponseDto;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.entity.ScheduleUser;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.repository.ScheduleRepository;
import com.sparta.scheduler.repository.ScheduleUserRepository;
import com.sparta.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleUserRepository scheduleUserRepository;

    public UserService(UserRepository userRepository, ScheduleRepository scheduleRepository, ScheduleUserRepository scheduleUserRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleUserRepository = scheduleUserRepository;
    }

    // 5-1. 유저 저장
    public UserResponseDto saveUser(UserRequestDto requestDto) {
        User user = new User(requestDto);

        User saveUser = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto(user);

        return userResponseDto;
    }

    // 5-1. 유저 단건 조회
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 유저가 존재하지 않습니다."));

        return new UserResponseDto(user);
    }

    // 5-1. 유저 전체 조회
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponseDto::new).toList();
    }


    // 5-1. 유저 수정
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = findUser(id);
        user.update(requestDto);

        return new UserResponseDto(user);

    }

    // 5-1. 유저 삭제
    public Long deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
        return id;
    }

    // 5-3. 일정에 담당자 추가
    public void assignUsersToSchedule(Long id, Long scheduleId, List<Long> managerUserList) {
        User writer = findUser(id); // 작성자 유저 확인
        Schedule schedule = findSchedule(scheduleId); // 일정 확인

        // 일정 작성자와 요청한 유저가 동일하지 않을 때
        if (!Objects.equals(id, schedule.getWriter().getId())) {
            throw new IllegalArgumentException("해당 일정의 작성자가 아닙니다.");
        }

        // 유저가 존재하는지 확인하고 ScheduleUser 엔티티를 생성하여 할당
        for (Long managerId : managerUserList) {
            User manager = findUser(managerId); // 존재하지 않는 유저일 경우 예외 처리
            if (manager == null) {
                throw new NoSuchElementException("존재하지 않는 유저 ID: " + managerId);
            }
            ScheduleUser scheduleUser = new ScheduleUser(schedule, manager);
            scheduleUserRepository.save(scheduleUser);
        }
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("해당 일정이 존재하지 않습니다."));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다. "));
    }
}
