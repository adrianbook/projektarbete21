package com.toiletuserservice.repo;

import com.toiletuserservice.domain.ToiletUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiletUserRepo extends JpaRepository<ToiletUser, Long> {
    ToiletUser findToiletUserByUsername(String username);
}
