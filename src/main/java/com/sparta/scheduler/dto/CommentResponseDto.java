package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String writer;
    private String comments;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.comments = comment.getComments();
        this.createDate = comment.getCreateDate();
        this.modifiedDate = comment.getModifiedDate();
    }

}
