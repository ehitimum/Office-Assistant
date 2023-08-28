package com.example.leave_management.api.v1.controller;

import com.example.leave_management.dto.RequestAndResponseDTO.LeaveType.LeaveTypeCreationResponse;
import com.example.leave_management.dto.RequestAndResponseDTO.LeaveType.NewLeaveType;
import com.example.leave_management.service.LeaveType.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class LeaveTypeController {
    private final LeaveTypeService service;
    @PostMapping("/create-leave-type")
    public ResponseEntity<LeaveTypeCreationResponse> addLeaveType(@Valid @RequestBody NewLeaveType request){
        return ResponseEntity.ok(service.createNewLeaveType(request));
    }
}




