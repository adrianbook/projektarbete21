package com.toiletuserservice.service;

import com.toiletuserservice.domain.Role;
import com.toiletuserservice.domain.ToiletUser;

import java.util.List;

public interface ToiletUserService {
    ToiletUser saveToiletUser(ToiletUser toiletUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    ToiletUser getToiletUser(String username);
    List<ToiletUser> getToiletUsers();
}
