package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    //할일, 담당자명, 비밀번호, 작성/수정일

    private Long id;
    private String contents;
    private String manager;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public Schedule(ScheduleRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
        this.createTime = LocalDateTime.now();
        this.modifiedTime = createTime;
    }

    // update 수정파트
    public void update(ScheduleRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
