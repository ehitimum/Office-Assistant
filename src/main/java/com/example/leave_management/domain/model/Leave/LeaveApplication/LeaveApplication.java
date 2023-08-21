package com.example.leave_management.domain.model.Leave.LeaveApplication;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long leaveApplicationId;
    private LocalDate leave_begin;
    private LocalDate leave_end;
    private Status status;
    private String remarks;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;

}
