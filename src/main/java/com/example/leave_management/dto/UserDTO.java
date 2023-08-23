package com.example.leave_management.dto;

import com.example.leave_management.domain.model.User.Role;
import com.example.leave_management.domain.model.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


        public UserDTO(User user) {
                this.userId = user.getUserId();
                this.userName = user.getUsername();
        }

}
