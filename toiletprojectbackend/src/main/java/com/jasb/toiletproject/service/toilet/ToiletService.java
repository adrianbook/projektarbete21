package com.jasb.toiletproject.service.toilet;

import com.jasb.entities.Toilet;
import com.jasb.toiletproject.exceptions.ToCloseToAnotherToiletException;
import com.jasb.toiletproject.exceptions.ToiletNotFoundException;

import java.util.List;

public interface ToiletService {
    List<Toilet> getAllToilets();
    Toilet addToilet(Toilet toilet) throws ToCloseToAnotherToiletException;
    Toilet getToiletById(long id) throws ToiletNotFoundException;

}
