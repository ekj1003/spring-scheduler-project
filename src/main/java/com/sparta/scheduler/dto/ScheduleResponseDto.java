package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ScheduleResponseDto {
    // ResponseDto는 반환해주는 dto이기 때문에 비밀번호 field가 없어야한다.
    private Long id;
    private String contents;
    private String manager;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public ScheduleResponseDto (Schedule schedule) {
        this.id = schedule.getId();
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createDate = schedule.getCreateDate();
        this.modifiedDate = schedule.getModifiedDate();
    }

}
