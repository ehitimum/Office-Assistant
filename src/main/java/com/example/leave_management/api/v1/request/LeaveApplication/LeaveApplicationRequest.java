package com.example.leave_management.api.v1.request.LeaveApplication;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveStatus;
import com.example.leave_management.domain.model.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApplicationRequest {
    private LocalDate leave_begin;
    private LocalDate leave_end;
    private Long leaveTypeId;
    private LeaveStatus status;
    private String remarks;
    private Long userId;
}
