package com.example.leave_management.api.v1.request.UserList;

import com.example.leave_management.domain.model.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedUserResponse {
    private Page<User> userPage;
    private String message;
}
