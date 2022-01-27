package com.jasb.toiletproject.repo;

import com.jasb.entities.ToiletUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is used only for testing purposes
 *
 * Written by JASB
 */
public interface ToiletUserRepo extends JpaRepository<ToiletUser, Long> {
    ToiletUser findToiletUserByUsername(String username);
}