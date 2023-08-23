package com.example.leave_management.api.v1.controller;

import com.example.leave_management.api.v1.request.LeaveBalance.CustomLeaveBalanceSetResponse;
import com.example.leave_management.api.v1.request.LeaveBalance.CustomBalanceSetter;
import com.example.leave_management.service.LeaveBalance.LeaveBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class CustomLeaveController {
    private final LeaveBalanceService service;
    @PostMapping("/custom-leave-balance/{userId}")
    public ResponseEntity<CustomLeaveBalanceSetResponse> setCustomLeaveBalance(@RequestBody CustomBalanceSetter request, @PathVariable Long userId){
        return ResponseEntity.ok(service.setCustomBalanceForNewUser(userId));
    }
}
