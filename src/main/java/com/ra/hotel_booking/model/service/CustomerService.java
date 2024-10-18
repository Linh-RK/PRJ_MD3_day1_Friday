package com.ra.hotel_booking.model.service;

import com.ra.hotel_booking.model.entity.Customer;

public interface CustomerService {
    boolean registerCustomer(Customer customer);
    Customer login(Customer customer);
}
