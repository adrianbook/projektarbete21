package com.jasb.toiletproject.repo;


import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public interface ToiletRepository extends JpaRepository<Toilet, Long> {
/*@PersistenceContext()
private EntityManager entity;
    public void addRatingBy(String toiletId, Rating rating);*/
}