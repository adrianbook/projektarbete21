package com.toiletuserservice.service;

import com.toiletuserservice.domain.Role;
import com.toiletuserservice.domain.ToiletUser;
import com.toiletuserservice.repo.RoleRepo;
import com.toiletuserservice.repo.ToiletUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ToiletUserServiceImpl implements ToiletUserService, UserDetailsService {

    private final ToiletUserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ToiletUser toiletUser = userRepo.findToiletUserByUsername(username);
        if (toiletUser == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in database {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        toiletUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(
                                                toiletUser.getUsername(),
                                                toiletUser.getPassword(),
                                                authorities
                                                );
    }
    @Override
    public ToiletUser saveToiletUser(ToiletUser toiletUser) {
        log.info("Saving new user {} to the database", toiletUser.getUsername());
        toiletUser.setPassword(passwordEncoder.encode(toiletUser.getPassword()));
        return userRepo.save(toiletUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}",roleName, username );
        ToiletUser toiletUser = userRepo.findToiletUserByUsername(username);
        Role role = roleRepo.findByName(roleName);
        toiletUser.getRoles().add(role);
    }

    @Override
    public ToiletUser getToiletUser(String username) {
        log.info("Fetching user {}", username );
        return userRepo.findToiletUserByUsername(username);
    }

    @Override
    public List<ToiletUser> getToiletUsers() {
        return userRepo.findAll();
    }

}
