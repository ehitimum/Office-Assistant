//package com.example.leave_management.Models.Entities.User.Balance;
//
//import com.example.leave_management.Models.Entities.User.User;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class UserLeaveBalance {
//    private Integer sickBalance;
//    private Integer earnedBalance;
//    private Integer negetiveBalance;
//
//    @OneToOne(
//            cascade = CascadeType.ALL
//    )
//    @JoinColumn(
//            name = "user_id",
//            referencedColumnName = "userId"
//    )
//    private User user;
//}
