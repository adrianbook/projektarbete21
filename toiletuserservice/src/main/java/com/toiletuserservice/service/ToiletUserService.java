package com.toiletuserservice.service;

import entities.Role;
import entities.ToiletUser;

import java.util.List;

public interface ToiletUserService {
    ToiletUser saveToiletUser(ToiletUser toiletUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    ToiletUser getToiletUser(String username);
    List<ToiletUser> getToiletUsers();
}
