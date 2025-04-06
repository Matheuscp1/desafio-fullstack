package com.simplesdental.product.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.simplesdental.product.exception.UserRoleException;

public enum UserRole {
    admin("admin"),
    user("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }

    @JsonCreator
    public static UserRole fromString(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.role.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new UserRoleException("Roles permitidos  USER");
    }
}
