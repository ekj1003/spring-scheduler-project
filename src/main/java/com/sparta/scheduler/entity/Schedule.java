package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="schedule")
@NoArgsConstructor
public class Schedule {
    //할일, 담당자명, 비밀번호, 작성/수정일
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="contents", nullable = false, length = 500)
    private String contents;

    @Column(name="manager", nullable = false)
    private String manager;

    @Column(name="password", nullable = false, length = 200)
    private String password;

    @Column(name="createTime", nullable = false)
    private LocalDateTime createTime;

    @Column(name="modifiedTime", nullable = false)
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
