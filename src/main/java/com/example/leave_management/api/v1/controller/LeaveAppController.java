package com.example.leave_management.api.v1.controller;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leave")
@RequiredArgsConstructor
public class LeaveAppController {
    @PostMapping("/leave-application")
    public void leaveApply(@RequestBody LeaveApplication leave){

    }
}
