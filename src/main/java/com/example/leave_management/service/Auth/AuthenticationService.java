package com.example.leave_management.service.Auth;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.domain.repository.LeaveApplicationRepository;
import com.example.leave_management.domain.repository.LeaveBalanceRepository;
import com.example.leave_management.dto.PaginationRequestsAnResponse.PageNumberRequestDTO;
import com.example.leave_management.dto.UpdateAccount.UpdatePasswordRequestDTO;
import com.example.leave_management.dto.UpdateAccount.UpdateUserNameRequestDTO;
import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.Auth.AuthenticationRequestDTO;
import com.example.leave_management.dto.Auth.RegisterRequestDTO;
import com.example.leave_management.dto.Auth.UserDTO;
import com.example.leave_management.exception.ApiResponse.ApiResponse;
import com.example.leave_management.security.JwtService;
import com.example.leave_management.service.LeaveBalance.LeaveBalanceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    public ApiResponse<String> register(RegisterRequestDTO request) {
        try
        {
            Optional<User> userExistsOptional = userRepository.findByEmail(request.getEmail());
            if (userExistsOptional.isPresent()) {
                return new ApiResponse<>(false, "This Email Address has taken!!", HttpStatus.NOT_FOUND.value(), null, null);

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
            return new ApiResponse<>(true, "New User is created successfully!!", HttpStatus.OK.value(), jwtToken, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to create a new user!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }

    }

    public ApiResponse<String> authenticate(AuthenticationRequestDTO request) {
        try
        {
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
            return new ApiResponse<>(true, "Authentication Success!!", HttpStatus.OK.value(), jwtToken, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Authentication Failed!!", HttpStatus.NOT_FOUND.value(), null,List.of(e.getMessage()));
        }

    }

    public ApiResponse<List<UserDTO>> getUserListWithPagination(PageNumberRequestDTO paginationRequest) {
        try{
            int pageNumber = paginationRequest.getCurrentPageNumber();
            int pageSize = 5;

            Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);

            Page<User> userPage = userRepository.findAll(pageRequest);

            String message = "Successfully retrieved user list with pagination.";

            return new ApiResponse<>(true, message, HttpStatus.OK.value(),
                    userPage.getContent().stream()
                    .map(this::convertToUserDTO)
                    .collect(Collectors.toList()), null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to retrieve user list!", HttpStatus.INTERNAL_SERVER_ERROR.value(),null, List.of(e.getMessage()));
        }

    }

    private UserDTO convertToUserDTO(User user) {
        return getUserDTO(user);
    }

    public ApiResponse<UserDTO> showUserLeaves(Long id){
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return new ApiResponse<>(true, "Successful!", HttpStatus.OK.value(), getUserDTO(user), null) ;
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage())) ;

        }

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
    public ApiResponse<UserDTO> updateUserName(Long userId, UpdateUserNameRequestDTO request) {
        try{
            User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
            user.setUserName(request.getUserName());
            userRepository.save(user);
            return new ApiResponse<>(true, "UserName is Updated!!", HttpStatus.OK.value(), getUserDTO(user), null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed To Update!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }

    }
    @Transactional
    public ApiResponse<UserDTO> updatePasswordField(Long userId, UpdatePasswordRequestDTO request) {
        try{
            User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
            if(passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);
                return new ApiResponse<>(true, "User Password is updated Successfully!!", HttpStatus.OK.value(), getUserDTO(user), null);
            }
            return new ApiResponse<>(false, "Old Password Not Matched!!", HttpStatus.BAD_REQUEST.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Update Failed!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }

    }

    @Transactional
    public ApiResponse<String> deleteUserInformation(Long userId) {
        try{
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
            return new ApiResponse<>(true, "User is successfully deleted along with all his information!!", HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to delete the user!!", HttpStatus.OK.value(), null, List.of(e.getMessage()));
        }
    }
}
