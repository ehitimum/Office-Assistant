package com.example.leave_management.api.v1.controller;

import com.example.leave_management.api.v1.request.LeaveApplication.ApplicationSubmissonResponse;
import com.example.leave_management.api.v1.request.LeaveApplication.LeaveApplicationRequest;
import com.example.leave_management.api.v1.request.LeaveType.LeaveTypeCreationResponse;
import com.example.leave_management.api.v1.request.LeaveType.NewLeaveType;
import com.example.leave_management.service.LeaveApplication.LeaveApplicationService;
import com.example.leave_management.service.LeaveType.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LeaveTypeController {
    private final LeaveTypeService service;
    @PostMapping("/create-leave-type")
    public ResponseEntity<LeaveTypeCreationResponse> addLeaveType(@RequestBody NewLeaveType request){
        return ResponseEntity.ok(service.createNewLeaveType(request));
    }
}




