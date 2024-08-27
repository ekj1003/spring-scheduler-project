package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name="writer", nullable = false, length = 20)
    private String writer;

    @Column(name="title", nullable = false, length = 30)
    private String title;

    @Column(name="contents", nullable = false, length = 500)
    private String contents;

    // 2-1. Schedule과 Comment 1:N 관계
    @OneToMany(mappedBy = "schedule",  cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

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
