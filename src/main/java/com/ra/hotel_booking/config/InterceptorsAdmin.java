package com.ra.hotel_booking.config;

import com.ra.hotel_booking.model.entity.Customer;
import com.ra.hotel_booking.model.entity.constants.RoleName;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterceptorsAdmin implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        Customer userLogin = (Customer) request.getSession().getAttribute("userLogin");
        if (userLogin != null)
        {
            if (userLogin.getRoles().stream().anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN)))
            {
                return true;
            }
            else
            {
                response.sendRedirect("/403");
                return false;
            }
        }
        else
        {
            response.sendRedirect("/login");
            return false;
        }
    }
}
