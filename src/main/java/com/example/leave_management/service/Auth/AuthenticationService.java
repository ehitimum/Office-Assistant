package com.example.leave_management.service.Auth;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.domain.repository.LeaveApplicationRepository;
import com.example.leave_management.domain.repository.LeaveBalanceRepository;
import com.example.leave_management.dto.PaginationRequestsAnResponse.PageNumberRequest;
import com.example.leave_management.dto.UpdateAccount.UpdatePasswordReq;
import com.example.leave_management.dto.UpdateAccount.UpdateUserNameReq;
import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.Auth.AuthenticationRequest;
import com.example.leave_management.dto.Auth.AuthenticationResponse;
import com.example.leave_management.dto.Auth.RegisterRequest;
import com.example.leave_management.dto.Auth.UserDTO;
import com.example.leave_management.security.JwtService;
import com.example.leave_management.service.LeaveBalance.LeaveBalanceService;
import jakarta.transaction.Transactional;
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
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, LeaveBalanceService leaveBalanceService,
                                 LeaveBalanceRepository leaveBalanceRepository,
                                 LeaveApplicationRepository leaveApplicationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.leaveBalanceService = leaveBalanceService;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveApplicationRepository = leaveApplicationRepository;
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
                .Deleted(false)
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
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.isDeleted()) {
            throw new RuntimeException("User is flagged as deleted.");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<UserDTO> getUserListWithPagination(PageNumberRequest paginationRequest) {
        int pageNumber = paginationRequest.getCurrentPageNumber();
        int pageSize = 5;

        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<User> userPage = userRepository.findAll(pageRequest);

        String message = "Successfully retrieved user list with pagination.";

        return userPage.getContent().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        return getUserDTO(user);
    }

    public UserDTO showUserLeaves(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return getUserDTO(user);
    }

    private UserDTO getUserDTO(User user) {
        LeaveBalance leaveBalance = user.getLeaveBalance();
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole(),
                leaveBalance != null ? leaveBalance.getSickLeaveBalance() : 0,
                leaveBalance != null ? leaveBalance.getEarnedLeaveBalance() : 0,
                leaveBalance != null ? leaveBalance.getNegativeBalance() : 0,
                user.isDeleted()
        );
    }

    @Transactional
    public UserDTO updateUserName(Long userId, UpdateUserNameReq request) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        user.setUserName(request.getUserName());
        userRepository.save(user);
        return getUserDTO(user);
    }
    @Transactional
    public UserDTO updatePasswordField(Long userId, UpdatePasswordReq request) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        if(passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        }
        return getUserDTO(user);
    }

    @Transactional
    public void deleteUserInformation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<LeaveApplication> leaveApplications = leaveApplicationRepository.findByUser(user);
        for (LeaveApplication leaveApplication : leaveApplications) {
            leaveApplication.setDeleted(true);
            leaveApplicationRepository.save(leaveApplication);
        }
        LeaveBalance balance = user.getLeaveBalance();
        if (balance != null) {
            balance.setDeleted(true);
            leaveBalanceRepository.save(balance);
        }
         user.setDeleted(true);
         userRepository.save(user);
    }
}
