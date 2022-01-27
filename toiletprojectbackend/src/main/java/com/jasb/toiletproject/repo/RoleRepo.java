package com.jasb.toiletproject.repo;

import com.jasb.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is only used for testing purposes.
 *
 * Written by JASB
 */
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
