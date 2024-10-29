package com.ra.hotel_booking.model.service.admin.admin;

import com.ra.hotel_booking.model.entity.Admin;
import com.ra.hotel_booking.model.entity.Customer;

public interface AdminService {
    boolean register(Admin admin);
    Admin login(Admin admin);
}
