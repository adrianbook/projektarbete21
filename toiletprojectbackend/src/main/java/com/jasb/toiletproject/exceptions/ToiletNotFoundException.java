package com.jasb.toiletproject.exceptions;

/**
 * Custom exception for when a toilet is not found
 * Written by JASB
 */
public class ToiletNotFoundException extends Throwable {
    public ToiletNotFoundException(long id) {
        super(String.format("Toilet with id: %d could not be found", id));
    }

}
