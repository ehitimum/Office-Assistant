package com.example.leave_management.dto.LeaveApplication;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationReqDTO {
    private Long leaveApplicationId;
    private LeaveStatus status;
    private String remarks;
}
