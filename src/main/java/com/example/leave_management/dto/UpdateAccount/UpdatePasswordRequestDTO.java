package com.example.leave_management.dto.UpdateAccount;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO {
    @NotNull
    private String oldPassword;
    @NotNull
    private String password;
}
