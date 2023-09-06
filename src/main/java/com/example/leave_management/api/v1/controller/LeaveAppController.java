package com.example.leave_management.api.v1.controller;

import com.example.leave_management.dto.LeaveApplication.LeaveApplicationRequestDTO;
import com.example.leave_management.dto.LeaveApplication.UpdateApplicationReqDTO;
import com.example.leave_management.service.LeaveApplication.LeaveApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Leave")
@RequiredArgsConstructor
public class LeaveAppController {
    private final LeaveApplicationService leaveApplicationService;
    @PostMapping("/leave-application/user/{userId}")
    public ResponseEntity<?> leaveApply(@PathVariable Long userId, @RequestBody LeaveApplicationRequestDTO request){
        return ResponseEntity.ok(leaveApplicationService.saveApplicationRequest(userId, request));
    }

    @PutMapping("/pending-approval")
    public ResponseEntity<?> allowRejectApplication(@RequestBody UpdateApplicationReqDTO request){
        return ResponseEntity.ok(leaveApplicationService.updateApplicationRequest(request));
    }

    @GetMapping("/pending-approval/{pageId}")
    public ResponseEntity<?> getAllPendingApplications(
            @PathVariable int pageId) {
        return ResponseEntity.ok(leaveApplicationService.getALlPendingApplications(pageId));
    }

    @GetMapping("/Application-List/{userId}&{pageId}")
    public ResponseEntity<?> getAllApplicationsByID(@PathVariable Long userId, @PathVariable int pageId){
        return ResponseEntity.ok(leaveApplicationService.getAllApplicationsOfAUser(userId,pageId));
    }

}
