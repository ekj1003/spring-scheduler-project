package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;


    public UserResponseDto (User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createDate = user.getCreateDate();
        this.modifiedDate = user.getModifiedDate();
    }
}
