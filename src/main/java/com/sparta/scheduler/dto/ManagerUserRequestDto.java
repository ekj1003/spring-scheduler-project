package com.sparta.scheduler.dto;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ManagerUserRequestDto {
    private Long id;
    private Long scheduleId;
    private ArrayList<Long> managerUserList;
}
