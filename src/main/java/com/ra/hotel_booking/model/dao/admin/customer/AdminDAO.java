package com.ra.hotel_booking.model.dao.admin.customer;

import com.ra.hotel_booking.model.entity.Admin;
import com.ra.hotel_booking.model.entity.Customer;

public interface AdminDAO {
    boolean register(Admin admin);
    Admin login(Admin admin);
}
