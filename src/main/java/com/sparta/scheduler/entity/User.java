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

    // 5-3. 중간 테이블(ScheduleUser) 담당자로 지정된 일정들
    // 이 유저가 작성한 일정의 담당자들이 들어있음.
    @OneToMany(mappedBy = "managerUser")
    private List<ScheduleUser> managerUserAndScheduleList = new ArrayList<>();


    public User(UserRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }
}
