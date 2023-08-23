package com.example.leave_management.api.v1.controller;

import com.example.leave_management.api.v1.request.Auth.AuthenticationResponse;
import com.example.leave_management.api.v1.request.Auth.RegisterRequest;
import com.example.leave_management.api.v1.request.LeaveApplication.ApplicationSubmissonResponse;
import com.example.leave_management.api.v1.request.LeaveApplication.LeaveApplicationRequest;
import com.example.leave_management.api.v1.request.LeaveApplication.updateApplicationReq;
import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.service.LeaveApplication.LeaveApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LeaveAppController {
    private final LeaveApplicationService leaveApplicationService;
    @PostMapping("/leave-application")
    public ResponseEntity<ApplicationSubmissonResponse> leaveApply(@RequestBody LeaveApplicationRequest request){
        return ResponseEntity.ok(leaveApplicationService.saveApplicationRequest(request));
    }

    @PutMapping("/pending-approval")
    public ResponseEntity<ApplicationSubmissonResponse> allowRejectApplication(@RequestBody updateApplicationReq request){
        return ResponseEntity.ok(leaveApplicationService.updateApplicationRequest(request));
    }
}
