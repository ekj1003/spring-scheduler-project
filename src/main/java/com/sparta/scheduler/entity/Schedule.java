package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
    //할일, 담당자명, 비밀번호, 작성/수정일
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="writer", nullable = false)
    private String writer;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="contents", nullable = false, length = 500)
    private String contents;


//    @Column(name="password", nullable = false, length = 200)
//    private String password;

    public Schedule(ScheduleRequestDto requestDto) {
        this.writer = requestDto.getWriter();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    // update 수정파트
    public void update(ScheduleRequestDto requestDto) {
        this.writer = requestDto.getWriter();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
