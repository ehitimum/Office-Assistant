package com.example.leave_management.dto.LeaveApplication;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApplicationRequestDTO {
    private LocalDate leave_begin;
    private LocalDate leave_end;
    private Long leaveTypeId;
    private String remarks;
}
