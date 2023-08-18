package com.example.leave_management.Models.Entities.Leave.LeaveType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long leaveTypeId;
    private String leaveTypeName;
}
