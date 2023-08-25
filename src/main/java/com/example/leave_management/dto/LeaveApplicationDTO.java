package com.example.leave_management.dto;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveStatus;
import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.domain.model.User.Role;
import com.example.leave_management.domain.model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class LeaveApplicationDTO {
    private Long leaveApplicationId;
    private LocalDate leaveBegin;
    private LocalDate leaveEnd;
    private Long leaveTypeId;
    private String leaveTypeName;
    private LeaveStatus leaveStatus;
    private String remarks;
    private Long userId;
    private Long days;
    private UserDTO user;


}



