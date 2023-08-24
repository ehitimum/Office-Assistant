package com.example.leave_management.dto;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveStatus;
import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.domain.model.User.Role;
import com.example.leave_management.domain.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplicationDTO {
    private Long leaveApplicationId;
    private LocalDate leaveBegin;
    private LocalDate leaveEnd;
    private LeaveType leaveTypeId;
    private LeaveStatus leaveStatus;
    private String remarks;
    private UserDTO userDTO;
}



