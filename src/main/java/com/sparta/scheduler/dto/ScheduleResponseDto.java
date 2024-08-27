package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ScheduleResponseDto {
    // ResponseDto는 반환해주는 dto이기 때문에 비밀번호 field가 없어야한다.
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private int commentCount;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public ScheduleResponseDto (Schedule schedule) {
        this.id = schedule.getId();
        this.writer = schedule.getWriter().getName(); // 일정을 생성한 유저의 이름
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.commentCount = schedule.getCommentList().size();
        this.createDate = schedule.getCreateDate();
        this.modifiedDate = schedule.getModifiedDate();
    }

}
