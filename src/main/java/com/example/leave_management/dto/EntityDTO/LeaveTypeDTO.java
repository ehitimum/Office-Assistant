package com.example.leave_management.dto.EntityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTypeDTO {
    private Long leaveTypeId;
    private String leaveTypeName;
}
