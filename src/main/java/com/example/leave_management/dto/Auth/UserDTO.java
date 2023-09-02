package com.example.leave_management.dto.Auth;

import com.example.leave_management.domain.model.User.Role;
import com.example.leave_management.domain.model.User.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

        private Long userId;
        private String userName;
        private String email;
        private Role role;
        private int sickLeaveBalance;
        private int earnedLeaveBalance;
        private int negativeBalance;
        private boolean Deleted;


        public UserDTO(User user) {
                this.userId = getUserId();
                this.userName = getUserName();
                this.email = getEmail();
                this.role = getRole();
                this.sickLeaveBalance = getSickLeaveBalance();
                this.earnedLeaveBalance = getEarnedLeaveBalance();
                this.negativeBalance = getNegativeBalance();
                this.Deleted = isDeleted();

        }


}
