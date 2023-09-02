package com.example.leave_management.dto.LeaveApplication;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import org.springframework.data.domain.Page;

public class GetAllPendingApplicationsResponseDTO {
    private Page<LeaveApplication> applicationList;
    private String msg;
}
