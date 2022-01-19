package com.jasb.toiletproject.exceptions;

public class ToiletNotFoundException extends Throwable {
    public ToiletNotFoundException(long id) {
        super(String.format("Toilet with id: %d could not be found", id));
    }

}
