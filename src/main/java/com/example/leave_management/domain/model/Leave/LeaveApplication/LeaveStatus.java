package com.example.leave_management.domain.model.Leave.LeaveApplication;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


public enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED;

    @Converter(autoApply = true)
    public static class LeaveStatusConverter implements AttributeConverter<LeaveStatus, Integer> {
        @Override
        public Integer convertToDatabaseColumn(LeaveStatus attribute) {
            switch (attribute){
                case PENDING -> {
                    return 0;
                }
                case APPROVED -> {
                    return 1;
                }
                case REJECTED -> {
                    return 2;
                }
                default -> throw new IllegalArgumentException("Unknown LeaveStatus" + attribute);
            }
        }

        @Override
        public LeaveStatus convertToEntityAttribute(Integer dbData) {
            switch (dbData){
                case 0 -> {
                    return PENDING;
                }
                case 1 -> {
                    return APPROVED;
                }
                case 2 -> {
                    return REJECTED;
                }
                default -> throw new IllegalArgumentException("Unknown database value for LeaveStatus" + dbData);
            }
        }
    }
}
