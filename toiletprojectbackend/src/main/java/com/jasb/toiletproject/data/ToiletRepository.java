package com.jasb.toiletproject.data;

import com.jasb.toiletproject.domain.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiletRepository extends JpaRepository<Toilet, Long> {

}