package com.jasb.toiletproject.security;


import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jasb.toiletproject.security.ApplicationUserPermission.TOILET_READ;
import static com.jasb.toiletproject.security.ApplicationUserPermission.TOILET_WRITE;

public enum ApplicationUserRole {
    APPUSER(Sets.newHashSet(TOILET_READ, TOILET_WRITE)),
    ADMIN(Sets.newHashSet(TOILET_READ, TOILET_WRITE));

    private Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(HashSet<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public void setPermissions(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }


    public Set<ApplicationUserPermission> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
