package com.example.leave_management.service.Holiday;

import com.example.leave_management.domain.model.Holidays.Holidays;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.HolidaysRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayRequestDTO;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayResponseDTO;
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

    public NewHolidayResponseDTO createNewHolidays(List<Holidays> requests) {
        repository.saveAll(requests);
        return NewHolidayResponseDTO.builder().msg("Success!").build();
    }

    public NewHolidayResponseDTO createOneHoliday(NewHolidayRequestDTO request) {
        var holiday = Holidays.builder()
                .holidayName(request.getHolidayName())
                .holidayStartDate(request.getHolidayStartDate())
                .totalHoliday(request.getTotalHoliday())
                .build();
        repository.save(holiday);
        return NewHolidayResponseDTO.builder().msg("Success!").build();
    }

    public NewHolidayResponseDTO updateHoliday(Long holidayId, Holidays updatedHoliday) {
        Optional<Holidays> holidayToUpdate = repository.findById(holidayId);
        if (holidayToUpdate.isPresent()) {
            Holidays existingHoliday = holidayToUpdate.get();
            repository.save(existingHoliday);
        }
        return NewHolidayResponseDTO.builder().msg("Success!").build();
    }



    public NewHolidayResponseDTO linkHolidaysToUser(Long userId, List<Holidays> holidays) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setHolidays(holidays);
            userRepository.save(user);
        }
        return NewHolidayResponseDTO.builder().msg("Success!").build();
    }
}
