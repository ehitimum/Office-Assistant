package com.example.leave_management.api.v1.controller;

import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.dto.LeaveType.LeaveTypeCreationResponse;
import com.example.leave_management.dto.LeaveType.NewLeaveType;
import com.example.leave_management.service.LeaveType.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/get-leave-type")
    public ResponseEntity<List<LeaveType>> showAllLeaveType(){
        return ResponseEntity.ok(service.showAllLeaveTypeName());
    }
}




