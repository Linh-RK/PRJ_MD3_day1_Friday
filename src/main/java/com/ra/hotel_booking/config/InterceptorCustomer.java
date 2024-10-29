package com.ra.hotel_booking.config;

import com.ra.hotel_booking.model.entity.Customer;
import com.ra.hotel_booking.model.entity.constants.RoleName;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class InterceptorCustomer implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        // Lấy đường dẫn hiện tại
        String path = request.getServletPath();

        // Kiểm tra nếu đường dẫn nằm trong danh sách loại trừ
        List<String> excludePaths = Arrays.asList("/user/login", "/user/signup", "/user/forgot-password",
                "/user/index", "/user/events", "/user/events-detail",
                "/user/about", "/user/contact", "/user/rooms-col-2", "/user/rooms-details/**");
        if (excludePaths.contains(path)) {
            return true;
        }

        Customer customerLogin = (Customer) request.getSession().getAttribute("customerLogin");
        if (customerLogin != null) {
            if (customerLogin.getRoles().stream().anyMatch(role -> role.getRoleName().equals(RoleName.CUSTOMER))) {
                return true;
            } else {
                response.sendRedirect("/403");
                return false;
            }
        } else {
            response.sendRedirect("/user/login");
            return false;
        }
    }
}
