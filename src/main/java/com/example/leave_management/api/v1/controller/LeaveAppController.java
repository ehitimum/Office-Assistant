package com.example.leave_management.api.v1.controller;

import com.example.leave_management.dto.LeaveApplication.ApplicationSubmissonResponseDTO;
import com.example.leave_management.dto.LeaveApplication.LeaveApplicationRequestDTO;
import com.example.leave_management.dto.LeaveApplication.UpdateApplicationReqDTO;
import com.example.leave_management.dto.LeaveApplication.LeaveApplicationDTO;
import com.example.leave_management.service.LeaveApplication.LeaveApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Leave")
@RequiredArgsConstructor
public class LeaveAppController {
    private final LeaveApplicationService leaveApplicationService;
    @PostMapping("/leave-application")
    public ResponseEntity<ApplicationSubmissonResponseDTO> leaveApply(@RequestBody LeaveApplicationRequestDTO request){
        return ResponseEntity.ok(leaveApplicationService.saveApplicationRequest(request));
    }

    @PutMapping("/pending-approval")
    public ResponseEntity<ApplicationSubmissonResponseDTO> allowRejectApplication(@RequestBody UpdateApplicationReqDTO request){
        return ResponseEntity.ok(leaveApplicationService.updateApplicationRequest(request));
    }

    @GetMapping("/pending-approval/{pageId}")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllPendingApplications(
            @PathVariable int pageId) {
        List<LeaveApplicationDTO> leaveApplicationDTOS = leaveApplicationService.getALlPendingApplications(pageId);
        return ResponseEntity.ok(leaveApplicationDTOS);
    }

    @GetMapping("/Application-List/{userId}&{pageId}")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllApplicationsByID(@PathVariable Long userId, @PathVariable int pageId){
        List<LeaveApplicationDTO> leaveApplicationDTOS = leaveApplicationService.getAllApplicationsOfAUser(userId,pageId);
        return ResponseEntity.ok(leaveApplicationDTOS);
    }

}
