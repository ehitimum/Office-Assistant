package com.example.leave_management.domain.model.Bills;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum BillStatus {
    PENDING,
    APPROVED,
    REJECTED;

    @Converter(autoApply = true)
    public static class BillStatusConverter implements AttributeConverter<BillStatus, Integer> {
        @Override
        public Integer convertToDatabaseColumn(BillStatus attribute) {
            switch (attribute) {
                case PENDING:
                    return 0;
                case APPROVED:
                    return 1;
                case REJECTED:
                    return 2;
                default:
                    throw new IllegalArgumentException("Unknown BillStatus: " + attribute);
            }
        }

        @Override
        public BillStatus convertToEntityAttribute(Integer dbData) {
            switch (dbData) {
                case 0:
                    return PENDING;
                case 1:
                    return APPROVED;
                case 2:
                    return REJECTED;
                default:
                    throw new IllegalArgumentException("Unknown database value for BillStatus: " + dbData);
            }
        }
    }
}
