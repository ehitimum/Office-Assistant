package com.example.leave_management.service.Auth;

import com.example.leave_management.api.v1.request.UserList.PageNumberRequest;
import com.example.leave_management.api.v1.request.UserList.PaginatedUserResponse;
import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.api.v1.request.Auth.AuthenticationRequest;
import com.example.leave_management.api.v1.request.Auth.AuthenticationResponse;
import com.example.leave_management.api.v1.request.Auth.RegisterRequest;
import com.example.leave_management.dto.UserDTO;
import com.example.leave_management.security.JwtService;
import com.example.leave_management.service.LeaveBalance.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LeaveBalanceService leaveBalanceService;
    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, LeaveBalanceService leaveBalanceService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.leaveBalanceService = leaveBalanceService;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> userExistsOptional = userRepository.findByEmail(request.getEmail());
        if (userExistsOptional.isPresent()) {
            return AuthenticationResponse.builder()
                    .token("The User Email Already Taken.")
                    .build();
        }
        var user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        leaveBalanceService.setCustomBalanceForNewUser(user.getUserId());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<UserDTO> getUserListWithPagination(PageNumberRequest paginationRequest) {
        int pageNumber = paginationRequest.getCurrentPageNumber();
        int pageSize = 5; // Number of users per page

        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize); // Adjusting page number to 0-based index

        Page<User> userPage = userRepository.findAll(pageRequest);

        String message = "Successfully retrieved user list with pagination.";

        List<UserDTO> userDTOs = userPage.getContent().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());

        return userDTOs;
    }

    private UserDTO convertToUserDTO(User user) {
        LeaveBalance leaveBalance = user.getLeaveBalance();
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                leaveBalance != null ? leaveBalance.getSickLeaveBalance() : 0,
                leaveBalance != null ? leaveBalance.getEarnedLeaveBalance() : 0,
                leaveBalance != null ? leaveBalance.getNegativeBalance() : 0
        );
    }

}
