package com.jasb.toiletproject.security;

public enum ApplicationUserPermission {
    TOILET_READ("toilet:read"),
    TOILET_WRITE("toilet:write");

    private final String permission;
    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
