package com.toiletuserservice.repo;

import entities.ToiletUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiletUserRepo extends JpaRepository<ToiletUser, Long> {
    ToiletUser findToiletUserByUsername(String username);
}
