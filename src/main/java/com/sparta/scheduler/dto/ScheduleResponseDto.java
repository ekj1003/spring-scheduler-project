package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ScheduleResponseDto {
    private Long id;
    private String contents;
    private String manager;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public ScheduleResponseDto (Schedule schedule) {
        this.id = schedule.getId();
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createTime = schedule.getCreateTime();
        this.modifiedTime = schedule.getModifiedTime();
    }

    // public ScheduleResponseDto (Long id, String contents, )
}
