package com.example.leave_management.api.v1.controller;


import com.example.leave_management.dto.Auth.AuthenticationRequestDTO;
import com.example.leave_management.dto.PaginationRequestsAnResponse.PageNumberRequestDTO;
import com.example.leave_management.dto.UpdateAccount.UpdatePasswordRequestDTO;
import com.example.leave_management.dto.UpdateAccount.UpdateUserNameRequestDTO;
import com.example.leave_management.service.Auth.AuthenticationService;
import com.example.leave_management.dto.Auth.RegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/user-list")
    public ResponseEntity<?> getUserListWithPagination(
            @RequestParam(name = "currentPageNumber") int currentPageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        PageNumberRequestDTO pageNumberRequest = new PageNumberRequestDTO(currentPageNumber);
        return ResponseEntity.ok(authenticationService.getUserListWithPagination(pageNumberRequest));
    }
    
    @GetMapping("/yearly-leave/{userId}")
    public ResponseEntity<?> getYearlyLeaveBalance(@PathVariable Long userId){
        return ResponseEntity.ok(authenticationService.showUserLeaves(userId));
    }

    @PutMapping("/changeUserName/{userId}")
    public ResponseEntity<?> changeUserName(@Valid @PathVariable Long userId, @Valid @RequestBody UpdateUserNameRequestDTO request){
        return ResponseEntity.ok(authenticationService.updateUserName(userId, request));
    }
    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<?> changePassword(@Valid @PathVariable Long userId, @Valid @RequestBody UpdatePasswordRequestDTO request){
        return ResponseEntity.ok(authenticationService.updatePasswordField(userId, request));

    }
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUserInformation(@Valid @PathVariable Long userId){
        return ResponseEntity.ok(authenticationService.deleteUserInformation(userId));
    }






}
