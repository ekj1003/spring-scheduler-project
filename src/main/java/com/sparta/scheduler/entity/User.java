package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    // 5-2. 일정 작성 유저명 필드 대신 유저 고유 식별자 필드
    @OneToMany(mappedBy = "writer")
    private List<Schedule> scheduleList = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<ScheduleUser> ScheduleUserList = new ArrayList<>();


    public User(UserRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }

    public void update(UserRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }
}
