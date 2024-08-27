package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.UserRequestDto;
import com.sparta.scheduler.dto.UserResponseDto;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다. "));
    }


}
