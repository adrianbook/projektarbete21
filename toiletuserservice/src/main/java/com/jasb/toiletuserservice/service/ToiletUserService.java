package com.jasb.toiletuserservice.service;

import com.jasb.entities.Role;
import com.jasb.entities.ToiletUser;

import java.util.List;

public interface ToiletUserService {
    ToiletUser saveToiletUser(ToiletUser toiletUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    ToiletUser getToiletUser(String username);
    List<ToiletUser> getToiletUsers();
}
