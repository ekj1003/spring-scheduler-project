
package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OneScheduleResponseDto { // 6-1. 일정 단건 조회 시 담당 유저들의 고유 식별자, 유저명, 이메일이 추가로 포함
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private int commentCount;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private List<ManagerUserDto> managerUsers;

    public OneScheduleResponseDto (Schedule schedule) {
        this.id = schedule.getId();
        this.writer = schedule.getWriter().getName(); // 일정을 생성한 유저의 이름
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.commentCount = schedule.getCommentList().size();
        this.createDate = schedule.getCreateDate();
        this.modifiedDate = schedule.getModifiedDate();
        this.managerUsers = schedule.getManagerUserAndScheduleList().stream()
                .map(scheduleUser -> new ManagerUserDto(scheduleUser.getManagerUser()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class ManagerUserDto {
        private Long id;
        private String name;
        private String email;

        public ManagerUserDto(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }

}
