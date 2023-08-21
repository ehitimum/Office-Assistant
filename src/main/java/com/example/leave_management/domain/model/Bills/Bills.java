package com.example.leave_management.domain.model.Bills;

import com.example.leave_management.domain.model.User.User;
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
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long billId;
    private String comment;
    @Enumerated(EnumType.STRING)
    private BillStatus billStatus;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;
}
