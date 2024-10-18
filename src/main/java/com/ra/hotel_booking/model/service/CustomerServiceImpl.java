package com.ra.hotel_booking.model.service;

import com.ra.hotel_booking.model.dao.CustomerDAO;
import com.ra.hotel_booking.model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;
    @Override
    public boolean registerCustomer(Customer customer) {
        return customerDAO.register(customer);
    }

    @Override
    public Customer login(Customer customer) {
        return customerDAO.login(customer);
    }
}
