package com.example.leave_management.service.Holiday;

import com.example.leave_management.domain.model.Holidays.Holidays;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.HolidaysRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayRequest;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayResponse;
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

    public List<Holidays> getAllHolidays() {
        return repository.findAll();
    }

    public NewHolidayResponse createNewHolidays(List<Holidays> requests) {
        repository.saveAll(requests);
        return NewHolidayResponse.builder().msg("Success!").build();
    }

    public NewHolidayResponse createOneHoliday(NewHolidayRequest request) {
        var holiday = Holidays.builder()
                .holidayName(request.getHolidayName())
                .holidayStartDate(request.getHolidayStartDate())
                .totalHoliday(request.getTotalHoliday())
                .build();
        repository.save(holiday);
        return NewHolidayResponse.builder().msg("Success!").build();
    }

    public NewHolidayResponse updateHoliday(Long holidayId, Holidays updatedHoliday) {
        Optional<Holidays> holidayToUpdate = repository.findById(holidayId);
        if (holidayToUpdate.isPresent()) {
            Holidays existingHoliday = holidayToUpdate.get();
            repository.save(existingHoliday);
        }
        return NewHolidayResponse.builder().msg("Success!").build();
    }



    public NewHolidayResponse linkHolidaysToUser(Long userId, List<Holidays> holidays) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setHolidays(holidays);
            userRepository.save(user);
        }
        return NewHolidayResponse.builder().msg("Success!").build();
    }
}
