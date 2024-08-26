package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="comment")
@NoArgsConstructor
public class Comment extends Timestamped{

    // Schedule : Comment 1대 다 관계

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성 유저명
    @Column(name="writer", nullable = false)
    private String writer;

    // 댓글 내용
    @Column(name="comments", nullable = false, length = 500)
    private String comments;


    // 2-1. Schedule과 Comment 1:N 관계
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    public Comment(CommentRequestDto requestDto) {
        this.writer = requestDto.getWriter();
        this.comments = requestDto.getComments();
    }

    public void update(CommentRequestDto requestDto) {
        this.writer = requestDto.getWriter();
        this.comments = requestDto.getComments();
    }


}
