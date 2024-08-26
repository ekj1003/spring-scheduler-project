package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.CommentResponseDto;
import com.sparta.scheduler.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 2-2. 댓글 저장
    @PostMapping("/{scheduleId}")
    public CommentResponseDto createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto requestDto) {
        return commentService.saveComment(scheduleId, requestDto);
    }

    // 2-2. 댓글 단건 조회
    @GetMapping("/{id}")
    public CommentResponseDto getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    // 2-2. 댓글 전체 조회
    @GetMapping("/schedule/{scheduleId}")
    public List<CommentResponseDto> getAllComments(@PathVariable Long scheduleId) {
        return commentService.getAllComments(scheduleId);
    }

    // 2-3. 댓글 수정
    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

}
