package com.eyetrade.cloud.util.constants;

public enum Role {
    BASIC_USER("BASIC USER"),
    TRADER_USER("TRADER USER");

    private String userRole;

    private Role(String userRole) {
        this.userRole = userRole;
    }
}
