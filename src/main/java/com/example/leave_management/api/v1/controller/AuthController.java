package com.example.leave_management.api.v1.controller;


import com.example.leave_management.dto.Auth.AuthenticationRequestDTO;
import com.example.leave_management.dto.Auth.AuthenticationResponseDTO;
import com.example.leave_management.dto.PaginationRequestsAnResponse.PageNumberRequestDTO;
import com.example.leave_management.dto.UpdateAccount.UpdatePasswordRequestDTO;
import com.example.leave_management.dto.UpdateAccount.UpdateUserNameRequestDTO;
import com.example.leave_management.dto.Auth.UserDTO;
import com.example.leave_management.service.Auth.AuthenticationService;
import com.example.leave_management.dto.Auth.RegisterRequestDTO;
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
    public ResponseEntity<AuthenticationResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/user-list")
    public ResponseEntity<List<UserDTO>> getUserListWithPagination(
            @RequestParam(name = "currentPageNumber") int currentPageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        PageNumberRequestDTO pageNumberRequest = new PageNumberRequestDTO(currentPageNumber);
        List<UserDTO> userDTOs = authenticationService.getUserListWithPagination(pageNumberRequest);

        return ResponseEntity.ok(userDTOs);
    }
    
    @GetMapping("/yearly-leave/{userId}")
    public ResponseEntity<UserDTO> getYearlyLeaveBalance(@PathVariable Long userId){
        UserDTO userDTO = authenticationService.showUserLeaves(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/changeUserName/{userId}")
    public ResponseEntity<UserDTO> changeUserName(@Valid @PathVariable Long userId, @Valid @RequestBody UpdateUserNameRequestDTO request){
        UserDTO userDTO = authenticationService.updateUserName(userId, request);
        return ResponseEntity.ok(userDTO);
    }
    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<UserDTO> changePassword(@Valid @PathVariable Long userId, @Valid @RequestBody UpdatePasswordRequestDTO request){
        UserDTO userDTO = authenticationService.updatePasswordField(userId, request);
        return ResponseEntity.ok(userDTO);

    }
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUserInformation(@Valid @PathVariable Long userId){
        authenticationService.deleteUserInformation(userId);
        return ResponseEntity.ok("Success!");
    }






}
