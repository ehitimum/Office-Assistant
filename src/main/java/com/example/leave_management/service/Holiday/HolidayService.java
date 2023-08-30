package com.example.leave_management.service.Holiday;

import com.example.leave_management.domain.model.Holidays.Holidays;
import com.example.leave_management.domain.repository.HolidaysRepository;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayRequest;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HolidayService {
    private final HolidaysRepository repository;

    public HolidayService(HolidaysRepository repository) {
        this.repository = repository;
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
}
