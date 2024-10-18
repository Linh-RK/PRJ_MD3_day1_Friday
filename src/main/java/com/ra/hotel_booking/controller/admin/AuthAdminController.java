package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.entity.Customer;
import com.ra.hotel_booking.model.entity.constants.RoleName;
import com.ra.hotel_booking.model.service.CustomerService;
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
@RequestMapping("/")
public class AuthAdminController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "/admin/login/register";
    }
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("customer") Customer customer) {
        customerService.registerCustomer(customer);
        return "/admin/login/login";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("customer", new Customer());
        return "/admin/login/login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("customer")Customer customer,Model model) {
        Customer customerLogin = customerService.login(customer);
        if(customerLogin != null) {
            if(customerLogin.getRoles().stream().anyMatch(c-> c.getRoleName().equals(RoleName.ADMIN))){
                return "redirect:/admin/index";
            } else {
                return "redirect:/user/index";
            }
        }
        model.addAttribute("customer", new Customer());
        return "/admin/login/login";
    }
    @GetMapping("/admin/index")
    public String index(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("roomTypes", roomTypeList);
        return "/admin/index";
    }

}
