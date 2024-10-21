package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.service.admin.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String getBookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "/admin/booking";
    }
}
