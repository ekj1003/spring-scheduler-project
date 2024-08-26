package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 1-1. 일정 저장
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    // 1-1. 일정 단건 조회
    public ScheduleResponseDto getOneSchedule(Long id) {
        // DB 조회 (비밀번호 필드를 제외)
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 할 일이 존재하지 않습니다."));

        return new ScheduleResponseDto(schedule);
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

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메도는 존재하지 않습니다. "));
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
