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
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public ScheduleResponseDto (Schedule schedule) {
        this.id = schedule.getId();
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createTime = schedule.getCreateTime();
        this.modifiedTime = schedule.getModifiedTime();
    }

    public ScheduleResponseDto(Long id, String contents, String manager, LocalDateTime createTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.contents = contents;
        this.manager = manager;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }
}
