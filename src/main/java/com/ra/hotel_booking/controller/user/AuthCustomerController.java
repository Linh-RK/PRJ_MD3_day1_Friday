package com.ra.hotel_booking.controller.user;

import com.ra.hotel_booking.model.entity.Admin;
import com.ra.hotel_booking.model.entity.Customer;
import com.ra.hotel_booking.model.entity.constants.RoleName;
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
@RequestMapping("/")
public class AuthCustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoomService roomService;

    //   REGISTER ----------------------------------------------
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "/user/login/signup";
    }
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("customer") Customer customer, Model model) {
        customerService.register(customer);
        return "/user/login/login";
    }
    //    LOGIN----------------------------------------------
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("customer", new Customer());
        return "/user/login/login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("customer")Customer customer,Model model) {
        try{
            Customer customerLogin = customerService.login(customer);
            if(customerLogin != null) {
                if(customerLogin.getRoles().stream().anyMatch(c-> c.getRoleName().equals(RoleName.CUSTOMER))){
                    return "redirect:/user/index";
                }
            }
            model.addAttribute("admin", new Admin());
            return "/user/login/login";
        }catch (Exception e){
            model.addAttribute("customer", customer);
            model.addAttribute("error", "Email or Password is incorrect");
            return "/user/login/login";
        }
    }
}
