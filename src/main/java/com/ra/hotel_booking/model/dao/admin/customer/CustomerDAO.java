package com.ra.hotel_booking.model.dao.admin.customer;

import com.ra.hotel_booking.model.entity.Customer;

public interface CustomerDAO {
    boolean register(Customer customer);
    Customer login(Customer customer);
}