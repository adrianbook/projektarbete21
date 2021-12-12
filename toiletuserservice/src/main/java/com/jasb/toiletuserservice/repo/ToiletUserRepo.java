package com.jasb.toiletuserservice.repo;

import com.jasb.entities.ToiletUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiletUserRepo extends JpaRepository<ToiletUser, Long> {
    ToiletUser findToiletUserByUsername(String username);
}
