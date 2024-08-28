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

    @Column(name="title", nullable = false, length = 30)
    private String title;

    @Column(name="contents", nullable = false, length = 500)
    private String contents;

    // 2-1. Schedule과 Comment 1:N 관계
    @OneToMany(mappedBy = "schedule",  cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();



    // 5-2. 일정 작성 유저명 필드 대신 유저 고유 식별자 필드
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    // 5-3. 중간 테이블(ScheduleUser) 생성 For ManyToMany with User
    // 6-2. 지연 로딩
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<ScheduleUser> managerUserAndScheduleList = new ArrayList<>();


    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    // update 수정파트
    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
