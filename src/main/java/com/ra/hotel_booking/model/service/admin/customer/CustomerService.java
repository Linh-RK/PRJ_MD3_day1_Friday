package com.ra.hotel_booking.model.service.admin.customer;

import com.ra.hotel_booking.model.entity.Customer;

public interface CustomerService {
    boolean register(Customer customer);
    Customer login(Customer customer);

}
