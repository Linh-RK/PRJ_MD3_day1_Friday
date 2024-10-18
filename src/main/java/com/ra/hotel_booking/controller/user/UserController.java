package com.ra.hotel_booking.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    //user HOME
    @GetMapping("/index")
    public String home(Model model) {
        return "/index";
    }
    // 1 user login
    @GetMapping("/login")
    public String login(Model model) {
        return "/user/login/login";
    }
    //  2 user
    @GetMapping("/register")
    public String register(Model model) {
        return "/user/login/signup";
    }
    //  3 user
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return "/user/login/forgot-password";
    }
    //  4 user
    @GetMapping("/about")
    public String about(Model model) {
        return "/user/about";
    }
    //  5 user
    @GetMapping("/blog-creative")
    public String blogCreative(Model model) {
        return "/user/blog-creative";
    }
    //  6 user
    @GetMapping("/blog-details")
    public String blogDetail(Model model) {
        return "/user/blog-details";
    }
    //  7 user
    @GetMapping("/booking-system")
    public String bookingSystem(Model model) {
        return "/user/booking-system";
    }
    //  8 user
    @GetMapping("/contact")
    public String contact(Model model) {
        return "/user/contact";
    }
    //  9 user
    @GetMapping("/events")
    public String events(Model model) {
        return "/user/events";
    }
    //  10 user
    @GetMapping("/events-details")
    public String eventsDetails(Model model) {
        return "/user/events-details";
    }
    //  11 user
    @GetMapping("/faq")
    public String faq(Model model) {
        return "/user/faq";
    }

    //  12 user
    @GetMapping("/gallery-3column")
    public String gallery(Model model) {
        return "/user/gallery-3column";
    }

    //  13 user
    @GetMapping("/pricing-tables")
    public String pricingTable(Model model) {
        return "/user/pricing-tables";
    }

    //  14 user
    @GetMapping("/rooms-col-2")
    public String room(Model model) {
        return "/user/rooms-col-2";
    }

    //  15 user
    @GetMapping("/rooms-details")
    public String roomDetails(Model model) {
        return "/user/rooms-details";
    }
    //  16 user
    @GetMapping("/staff")
    public String staff(Model model) {
        return "/user/staff";
    }
    //  17 user
    @GetMapping("/typography")
    public String typography(Model model) {
        return "/user/typography";
    }
}
