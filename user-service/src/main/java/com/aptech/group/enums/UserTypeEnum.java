package com.aptech.group.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.StringUtils;

public enum UserTypeEnum {
    CUSTOMER("CUSTOMER"),
    EMPLOYEE("EMPLOYEE");

    public final String value;

    UserTypeEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static UserTypeEnum getIgnoreCase(String value) {
        if (StringUtils.hasText(value)) {
            return valueOf(value.toUpperCase());
        }
        return null;
    }
}
