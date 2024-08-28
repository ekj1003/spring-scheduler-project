package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.OneScheduleResponseDto;
import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.repository.ScheduleRepository;
import com.sparta.scheduler.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    // 1-1. 일정 저장
    // 5-2. 일정 작성자 아이디 받아서, 유저 고유 식별자 필드를 가지도록.
    public ScheduleResponseDto createSchedule(Long userId, ScheduleRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        Schedule schedule = new Schedule(requestDto);
        schedule.setWriter(user);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    // 1-1. 일정 단건 조회
    public OneScheduleResponseDto getOneSchedule(Long id) { // 6-1. 일정 단건 조회 시 담당 유저들의 고유 식별자, 유저명, 이메일이 추가로 포함
        // DB 조회 (비밀번호 필드를 제외)
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 할 일이 존재하지 않습니다."));

        return new OneScheduleResponseDto(schedule);
    }


    // 1-1. 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {

        // 해당 스케줄이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        schedule.update(requestDto);

        return new ScheduleResponseDto(schedule);
    }

    // 1-1. 일정 삭제
    public Long deleteSchedule(Long id) {

        // 해당 스케줄이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        scheduleRepository.delete(schedule);

        return id;
    }

    // 3-1. 일정 페이징 조회
    public Page<ScheduleResponseDto> getSchedules(int page, int size) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedDate");
        // 3-2. 수정일 기준 내림차순

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Schedule> scheduleList;

        scheduleList = scheduleRepository.findAll(pageable);

        return scheduleList.map(ScheduleResponseDto::new);

    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 할 일은 존재하지 않습니다. "));
    }

//    public List<ScheduleResponseDto> getSchedules(LocalDate modifiedDate, String manager) {
//        List<Schedule> schedules;
//        if (modifiedDate != null && manager != null) {
//            schedules = scheduleRepository.findByManagerAndModifiedDateBetween(manager, modifiedDate.atStartOfDay(), modifiedDate.atStartOfDay().plusDays(1).minusNanos(1));
//        }
//        else if (modifiedDate != null) {
//            schedules = scheduleRepository.findByModifiedDateBetween(modifiedDate.atStartOfDay(), modifiedDate.atStartOfDay().plusDays(1).minusNanos(1));
//        }
//        else if (manager != null) {
//            schedules = scheduleRepository.findByManager(manager);
//        }
//        else {
//            schedules = scheduleRepository.findAll();
//        }
//        return schedules.stream().map(ScheduleResponseDto::new).toList();
//    }



}
