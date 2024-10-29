package com.ra.hotel_booking.model.service.admin.admin;

import com.ra.hotel_booking.model.dao.admin.customer.AdminDAO;
import com.ra.hotel_booking.model.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private HttpSession httpSession;
    @Override
    public boolean register(Admin admin) {
        return adminDAO.register(admin);
    }

    @Override
    public Admin login(Admin admin) {
        Admin adminLogin = adminDAO.login(admin);
        httpSession.setAttribute("adminLogin", adminLogin);
        return adminLogin;
    }
}
