package com.example.leave_management.api.v1.controller;


import com.example.leave_management.dto.RequestAndResponseDTO.Auth.AuthenticationRequest;
import com.example.leave_management.dto.RequestAndResponseDTO.Auth.AuthenticationResponse;
import com.example.leave_management.dto.RequestAndResponseDTO.PaginationRequestsAnResponse.PageNumberRequest;
import com.example.leave_management.dto.RequestAndResponseDTO.UpdateAccount.UpdatePasswordReq;
import com.example.leave_management.dto.RequestAndResponseDTO.UpdateAccount.UpdateUserNameReq;
import com.example.leave_management.dto.EntityDTO.UserDTO;
import com.example.leave_management.service.Auth.AuthenticationService;
import com.example.leave_management.dto.RequestAndResponseDTO.Auth.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/paged")
    public ResponseEntity<List<UserDTO>> getUserListWithPagination(
            @RequestBody PageNumberRequest paginationRequest) {
        List<UserDTO> userDTOs = authenticationService.getUserListWithPagination(paginationRequest);

        return ResponseEntity.ok(userDTOs);
    }
    
    @GetMapping("/yearly-leave/{userId}")
    public ResponseEntity<UserDTO> getYearlyLeaveBalance(@PathVariable Long userId){
        UserDTO userDTO = authenticationService.showUserLeaves(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/changeUserName/{userId}")
    public ResponseEntity<UserDTO> changeUserName(@Valid @PathVariable Long userId, @Valid @RequestBody UpdateUserNameReq request){
        UserDTO userDTO = authenticationService.updateUserName(userId, request);
        return ResponseEntity.ok(userDTO);
    }
    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<UserDTO> changePassword(@Valid @PathVariable Long userId, @Valid @RequestBody UpdatePasswordReq request){
        UserDTO userDTO = authenticationService.updatePasswordField(userId, request);
        return ResponseEntity.ok(userDTO);

    }




}
