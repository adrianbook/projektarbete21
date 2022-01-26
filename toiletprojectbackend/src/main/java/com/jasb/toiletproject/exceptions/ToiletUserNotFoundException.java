package com.jasb.toiletproject.exceptions;


/**
 * Custom exception for when a toilet user is not found
 * Written by JASB
 */
public class ToiletUserNotFoundException extends Throwable {

    public ToiletUserNotFoundException(Throwable cause) {
        super(cause);
    }
}
