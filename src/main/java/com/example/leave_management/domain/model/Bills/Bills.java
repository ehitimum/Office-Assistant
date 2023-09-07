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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    private String billingTitle;
    private String comment;
    private Integer billCost;
    @Enumerated(EnumType.ORDINAL)
    private BillStatus billStatus;
    @Column(columnDefinition = "boolean default false")
    private boolean Deleted;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;
}
