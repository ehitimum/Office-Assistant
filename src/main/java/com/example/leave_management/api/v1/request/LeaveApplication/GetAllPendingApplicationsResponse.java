package com.example.leave_management.api.v1.request.LeaveApplication;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import org.springframework.data.domain.Page;

public class GetAllPendingApplicationsResponse {
    private Page<LeaveApplication> applicationList;
    private String msg;
}
