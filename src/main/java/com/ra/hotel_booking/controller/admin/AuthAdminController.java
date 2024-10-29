package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.entity.Admin;
import com.ra.hotel_booking.model.entity.Customer;
import com.ra.hotel_booking.model.entity.constants.RoleName;
import com.ra.hotel_booking.model.service.admin.admin.AdminService;
import com.ra.hotel_booking.model.service.admin.customer.CustomerService;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ra.hotel_booking.controller.admin.RoomController.availabilityStatusList;
import static com.ra.hotel_booking.controller.admin.RoomController.roomTypeList;

@Controller
@RequestMapping("/admin")
public class AuthAdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoomService roomService;

    //   REGISTER ----------------------------------------------
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("admin", new Admin());
        return "/admin/login/register";
    }
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("admin") Admin admin, Model model) {
        adminService.register(admin);
        return "/admin/login/login";
    }
//    LOGIN----------------------------------------------
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("admin", new Admin());
        return "/admin/login/login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("admin")Admin admin,Model model) {
        try{
            Admin adminLogin = adminService.login(admin);
            if(adminLogin != null) {
                if(adminLogin.getRoles().stream().anyMatch(c-> c.getRoleName().equals(RoleName.ADMIN))){

                    return "redirect:/admin/index";
                }
            }
            model.addAttribute("admin", new Admin());
            return "/admin/login/login";
        }catch (Exception e){
            model.addAttribute("admin", new Admin());
            model.addAttribute("error", "Email or Password is incorrect");
            return "/admin/login/login";
        }
    }
    //    ADMIN HOME----------------------------------------------
    @GetMapping("/admin/index")
    public String index(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("roomTypes", roomTypeList);
        return "/admin/index";
    }

}
