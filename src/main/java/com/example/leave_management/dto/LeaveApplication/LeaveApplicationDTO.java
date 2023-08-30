package com.example.leave_management.dto.LeaveApplication;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveStatus;
import com.example.leave_management.dto.Auth.UserDTO;
import lombok.*;

import java.time.LocalDate;

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



