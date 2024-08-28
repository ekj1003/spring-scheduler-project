package com.sparta.scheduler.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "schedule_user")
@NoArgsConstructor
public class ScheduleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY) // 6-2. 지연 로딩 설정
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch=FetchType.LAZY) // 6-2. 지연 로딩 설정
    @JoinColumn(name = "manager_user_id")
    private User managerUser;

    public ScheduleUser(Schedule schedule, User managerUser) {
        this.schedule = schedule;
        this.managerUser = managerUser;
    }
}
