package com.jasb.toiletproject.repo;

import com.jasb.entities.ToiletUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ToiletUser, Long> {
    ToiletUser findByUsername(String username);
}
