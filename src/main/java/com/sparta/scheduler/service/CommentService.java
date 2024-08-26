package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.CommentResponseDto;
import com.sparta.scheduler.entity.Comment;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.repository.CommentRepository;
import com.sparta.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    // 2-2. 댓글 저장
    @Transactional
    public CommentResponseDto saveComment(Long scheduleId, CommentRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. ")
        );

        Comment comment = new Comment(requestDto);
        comment.setSchedule(schedule);

        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    // 2-2. 댓글 단건 조회
    public CommentResponseDto getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("해당하는 댓글이 존재하지 않습니다."));

        return new CommentResponseDto(comment);

    }


    // 2-2. 댓글 전체 조회
    public List<CommentResponseDto> getAllComments(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);
        return comments.stream().map(CommentResponseDto::new).toList();
    }

    // 2-3. 댓글 수정
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findComment(id);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }


}
