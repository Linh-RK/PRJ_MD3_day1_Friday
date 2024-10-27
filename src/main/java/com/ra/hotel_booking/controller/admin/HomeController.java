package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.entity.RoomImages;
import com.ra.hotel_booking.model.service.admin.room.RoomImagesService;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomImagesService roomImagesService;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Hotel Booking");
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("roomImage", roomImagesService.findAll());
//        return "/test";
        return "/user/index";
    }

}
