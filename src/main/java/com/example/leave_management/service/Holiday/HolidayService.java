package com.example.leave_management.service.Holiday;

import com.example.leave_management.domain.model.Holidays.Holidays;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.HolidaysRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayRequestDTO;
import com.example.leave_management.exception.ApiResponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {
    private final HolidaysRepository repository;
    private final UserRepository userRepository;

    public HolidayService(HolidaysRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public ApiResponse<List<Holidays>> getAllHolidays() {
        try{
            return new ApiResponse<>(true, "All Holidays!", HttpStatus.OK.value(), repository.findAll(), null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed To Get Holiday List!", HttpStatus.INTERNAL_SERVER_ERROR.value(),null,  List.of(e.getMessage()));
        }
    }

    public ApiResponse<String> createNewHolidays(List<Holidays> requests) {
        try
        {
            repository.saveAll(requests);
            return new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to save new list of holidays!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }
    }

    public ApiResponse<String> createOneHoliday(NewHolidayRequestDTO request) {
        try {
            var holiday = Holidays.builder()
                    .holidayName(request.getHolidayName())
                    .holidayStartDate(request.getHolidayStartDate())
                    .totalHoliday(request.getTotalHoliday())
                    .build();
            repository.save(holiday);
            return new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to save the new holiday!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }
    }

    public ApiResponse<String> updateHoliday(Long holidayId, NewHolidayRequestDTO request) {
        try {
            Holidays holidays = repository.findById(holidayId).orElseThrow(()->new RuntimeException("Holiday Id not found."));
            holidays.setHolidayName(request.getHolidayName());
            holidays.setHolidayStartDate(request.getHolidayStartDate());
            holidays.setTotalHoliday(request.getTotalHoliday());
            repository.save(holidays);
            return new ApiResponse<>(true, "Success!!", HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to update!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }
    }



    public ApiResponse<String> linkHolidaysToUser(Long userId, List<Long> holidayIds) {
        try
        {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<Holidays> holidays = repository.findAllById(holidayIds);
                user.setHolidays(holidays);
                userRepository.save(user);
                return new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), null, null);
            }
            return new ApiResponse<>(false, "User Not Found!", HttpStatus.NOT_FOUND.value(), null, null);

        }catch (Exception e){
            return new ApiResponse<>(false, "Failed!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }
    }

    public ApiResponse<List<Object[]>> getHolidayIdsByUserId(Long userId) {
        try
        {
            return new ApiResponse<>(true, "Success!!", HttpStatus.OK.value(), repository.findHolidayIdsByUserId(userId), null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }

    }
}
