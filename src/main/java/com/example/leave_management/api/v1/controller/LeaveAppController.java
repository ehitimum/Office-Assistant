package com.example.leave_management.api.v1.controller;

import com.example.leave_management.api.v1.request.Auth.AuthenticationResponse;
import com.example.leave_management.api.v1.request.Auth.RegisterRequest;
import com.example.leave_management.api.v1.request.LeaveApplication.ApplicationSubmissonResponse;
import com.example.leave_management.api.v1.request.LeaveApplication.GetAllPendingApplicationsResponse;
import com.example.leave_management.api.v1.request.LeaveApplication.LeaveApplicationRequest;
import com.example.leave_management.api.v1.request.LeaveApplication.updateApplicationReq;
import com.example.leave_management.api.v1.request.PaginationRequestsAnResponse.PageNumberRequest;
import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.dto.LeaveApplicationDTO;
import com.example.leave_management.dto.UserDTO;
import com.example.leave_management.service.LeaveApplication.LeaveApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Leave")
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
