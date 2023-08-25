package com.example.leave_management.api.v1.controller;


import com.example.leave_management.api.v1.request.Auth.AuthenticationRequest;
import com.example.leave_management.api.v1.request.Auth.AuthenticationResponse;
import com.example.leave_management.api.v1.request.PaginationRequestsAnResponse.PageNumberRequest;
import com.example.leave_management.dto.UserDTO;
import com.example.leave_management.service.Auth.AuthenticationService;
import com.example.leave_management.api.v1.request.Auth.RegisterRequest;
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
}
