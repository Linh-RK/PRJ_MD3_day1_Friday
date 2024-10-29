package com.ra.hotel_booking.model.service.admin.customer;

import com.ra.hotel_booking.model.dao.admin.customer.AdminDAO;
import com.ra.hotel_booking.model.dao.user.customer.CustomerDAO;
import com.ra.hotel_booking.model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private HttpSession httpSession;

    @Override
    public boolean register(Customer customer) {
        return customerDAO.register(customer);
    }

    @Override
    public Customer login(Customer customer) {
        Customer customerLogin = customerDAO.login(customer);
        httpSession.setAttribute("customerLogin", customerLogin);
        return customerLogin;
    }
}
