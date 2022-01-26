package com.jasb.toiletuserservice.service;

import com.jasb.entities.Role;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletuserservice.repo.RoleRepo;
import com.jasb.toiletuserservice.repo.ToiletUserRepo;
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

/**
 * Implementation of the ToiletUserService interface and the UserDetailsService interface.
 * Uses the JPA repositories ToiletUserRepo and RoleRepo to interact with the database
 * Uses passwordEncoder to secure the passwords
 * Written by JASB
 */
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ToiletUserServiceImpl implements ToiletUserService, UserDetailsService {

    private final ToiletUserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method that searches for a user by username
     * @Overrie from UserDetailsService
     * @param username
     * @return userdetails based on the responce from database
     * @throws UsernameNotFoundException
     */
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

    /**
     * Saves a new ToiletUser to the database
     * Sets default authority to ROLE_APPUSER
     * @Override from ToiletUserService
     * @param toiletUser
     * @return a representation of the created ToiletUser
     */
    @Override
    public ToiletUser saveToiletUser(ToiletUser toiletUser) {
        log.info("Saving new user {} to the database", toiletUser.getUsername());
        toiletUser.setPassword(passwordEncoder.encode(toiletUser.getPassword()));
        toiletUser.getRoles().add(roleRepo.findByName("ROLE_APPUSER"));
        return userRepo.save(toiletUser);
    }

    /**
     * Saves a new role to the database
     * @param role
     * @return a representation of the created Role
     */
    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    /**
     * adds a new Role to a User
     * @param username
     * @param rolename
     */
    @Override
    public ToiletUser addRoleToUser(String username, String rolename) {
        log.info("Adding role {} to user {}",rolename, username );
        ToiletUser toiletUser = userRepo.findToiletUserByUsername(username);
        Role role = roleRepo.findByName(rolename);
        toiletUser.getRoles().add(role);
        return toiletUser;
    }

    /**
     * Gets a toiletuser from the database based on a username
     * @param username
     * @return a toiletUser
     */
    @Override
    public ToiletUser getToiletUser(String username) {
        log.info("Fetching user {}", username );
        return userRepo.findToiletUserByUsername(username);
    }

    /**
     * Gets all toiletUsers
     * @return a list of ToiletUsers
     */
    @Override
    public List<ToiletUser> getToiletUsers() {
        return userRepo.findAll();
    }

    /**
     * adds the blocked role to a user and sets blocked true
     * @param toiletUser
     * @return the blocked user
     */
    @Override
    public ToiletUser blockToiletUser(ToiletUser toiletUser) {
        toiletUser.setBlocked(true);
        Collection<Role> roles = toiletUser.getRoles();
        roles.clear();
        roles.add(roleRepo.findByName("BLOCKED"));
        toiletUser.setRoles(roles);
        return userRepo.save(toiletUser);
    }

    /**
     * sets blocked to false and sets role to APPUSER
     * @param toiletUser
     * @return
     */
    @Override
    public ToiletUser unBlockToiletUser(ToiletUser toiletUser) {
        toiletUser.setBlocked(false);
        Collection<Role> roles = toiletUser.getRoles();
        roles.clear();
        roles.add(roleRepo.findByName("ROLE_APPUSER"));
        toiletUser.setRoles(roles);
        return userRepo.save(toiletUser);
    }
}
