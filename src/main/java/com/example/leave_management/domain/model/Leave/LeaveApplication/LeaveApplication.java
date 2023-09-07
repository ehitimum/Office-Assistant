package com.example.leave_management.domain.model.Leave.LeaveApplication;

import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
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
@Entity
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveApplicationId;

    private LocalDate leave_begin;
    private LocalDate leave_end;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "leave_type_id",
            referencedColumnName = "leaveTypeId"
    )
    private LeaveType leaveTypeId;
    @Enumerated(EnumType.ORDINAL)
    private LeaveStatus leaveStatus;
    private String remarks;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;
    @Column(columnDefinition = "boolean default false")
    private boolean Deleted;

}
