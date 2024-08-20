package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    public ScheduleResponseDto getOneSchedule(Long id) {
        // DB 조회 (비밀번호 필드를 제외)
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 할 일이 존재하지 않습니다."));

        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> getSchedules(LocalDate modifiedDate, String manager) {
        List<Schedule> schedules;
        if (modifiedDate != null && manager != null) {
            schedules = scheduleRepository.findByManagerAndModifiedDateBetween(manager, modifiedDate.atStartOfDay(), modifiedDate.atStartOfDay().plusDays(1).minusNanos(1));
        }
        else if (modifiedDate != null) {
            schedules = scheduleRepository.findByModifiedDateBetween(modifiedDate.atStartOfDay(), modifiedDate.atStartOfDay().plusDays(1).minusNanos(1));
        }
        else if (manager != null) {
            schedules = scheduleRepository.findByManager(manager);
        }
        else {
            schedules = scheduleRepository.findAll();
        }
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }


    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String password, ScheduleRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        if(password.equals(schedule.getPassword())) {
            // memo 내용 수정
            schedule.update(requestDto);

            return new ScheduleResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    public Long deleteSchedule(Long id, String password) {

        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        if(password.equals(schedule.getPassword())) {
            // schedule 삭제
            scheduleRepository.delete(schedule);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메도는 존재하지 않습니다. "));
    }


}
