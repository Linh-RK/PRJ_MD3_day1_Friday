package com.ra.hotel_booking.model.service;

import org.mindrot.jbcrypt.BCrypt;

public class Test {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("123456",BCrypt.gensalt(10)));
    }
}
