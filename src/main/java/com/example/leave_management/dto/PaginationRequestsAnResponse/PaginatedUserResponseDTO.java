package com.example.leave_management.dto.PaginationRequestsAnResponse;

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
public class PaginatedUserResponseDTO {
    private Page<User> userPage;
    private String message;
}
