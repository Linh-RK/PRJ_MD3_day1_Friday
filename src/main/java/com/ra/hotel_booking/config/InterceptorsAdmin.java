//package com.ra.hotel_booking.config;
//
//import com.ra.hotel_booking.model.entity.Admin;
//import com.ra.hotel_booking.model.entity.Customer;
//import com.ra.hotel_booking.model.entity.constants.RoleName;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//import java.util.List;
//
//public class InterceptorsAdmin implements HandlerInterceptor
//{
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
//    { // Lấy đường dẫn hiện tại
//        String path = request.getServletPath();
//
//        // Kiểm tra nếu đường dẫn nằm trong danh sách loại trừ
//        List<String> excludePaths = Arrays.asList("/admin/login", "/admin/register", "/admin/forgot-password");
//        if (excludePaths.contains(path)) {
//            return true;
//        }
//
//        Admin adminLogin = (Admin) request.getSession().getAttribute("adminLogin");
//        if (adminLogin != null)
//        {
//            if (adminLogin.getRoles().stream().anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN)))
//            {
//                return true;
//            }
//            else
//            {
//                response.sendRedirect("/403");
//                return false;
//            }
//        }
//        else
//        {
//            response.sendRedirect("/admin/login");
//            return false;
//        }
//    }
//}
