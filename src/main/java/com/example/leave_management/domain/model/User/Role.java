package com.example.leave_management.domain.model.User;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum Role {
    EMPLOYEE,
    ADMIN,
    SUPERADMIN;

    @Converter(autoApply = true)
    public static class RoleConverter implements AttributeConverter<Role, Integer> {
        @Override
        public Integer convertToDatabaseColumn(Role attribute) {
            switch (attribute) {
                case EMPLOYEE:
                    return 0;
                case ADMIN:
                    return 1;
                case SUPERADMIN:
                    return 2;
                default:
                    throw new IllegalArgumentException("Unknown Role: " + attribute);
            }
        }

        @Override
        public Role convertToEntityAttribute(Integer dbData) {
            switch (dbData) {
                case 0:
                    return EMPLOYEE;
                case 1:
                    return ADMIN;
                case 2:
                    return SUPERADMIN;
                default:
                    throw new IllegalArgumentException("Unknown database value for Role: " + dbData);
            }
        }
    }
}
