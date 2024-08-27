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

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "manager_user_id")
    private User managerUser;

    public ScheduleUser(Schedule schedule, User managerUser) {
        this.schedule = schedule;
        this.managerUser = managerUser;
    }
}
