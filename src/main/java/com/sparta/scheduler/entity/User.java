package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

//    @OneToMany(mappedBy = "creator")
//    private List<Schedule> createdSchedules = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<ScheduleUser> assignedSchedules = new ArrayList<>();


    public User(UserRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }

    public void update(UserRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }
}
