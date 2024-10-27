package com.ra.hotel_booking.controller.user;

import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.RoomImages;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
import com.ra.hotel_booking.model.service.UploadFile.UploadFileService;
import com.ra.hotel_booking.model.service.admin.room.RoomImagesService;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomImagesService roomImagesService;
    //user HOME
    @GetMapping("/index")
    public String home(Model model) {
        model.addAttribute("rooms", roomService.findAll());
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
    public int page;
    public static String oldNameRoom = "";
    public int totalPages;
    public static List<RoomTypeName> roomTypeList = Arrays.asList(RoomTypeName.values());
    public static List<AvailabilityStatus> availabilityStatusList = Arrays.asList(AvailabilityStatus.values());
    @GetMapping("/rooms-col-2")
    public String index(@ModelAttribute("search") Search search,
                        @ModelAttribute("roomImages") RoomImages roomImages,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        Model model) {
        List<Room> roomList;

        if (search.getPriceMax() == null) {
            search.setPriceMax(roomService.maxPrice());
        }
        if (search.getPriceMax() == null) {
            search.setPriceMax(roomService.maxPrice());
        }
        int totalPages = roomService.totalPages(search);

        if (page + 1 > totalPages || page < 0) {
            page = totalPages - 1;
        }

        roomList = roomService.findAllPerPage(page, search);
        model.addAttribute("roomImages", roomImages);
        model.addAttribute("rooms", roomList);
        model.addAttribute("search", search);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        return "/user/rooms-col-2";

    }

    //  15 user

    @GetMapping("/rooms-details/{id}")
    public String roomDetails(Model model,
                               @PathVariable("id") int roomId) {
        Room room = roomService.findById(roomId);
        List<String> amenities = room.getAmenities();
                model.addAttribute("room", room);
        model.addAttribute("id", roomId);
        model.addAttribute("amenities", amenities);
        for (int i = 0; i < amenities.size(); i++) {
            System.out.println(room.getAmenities().get(i));
        }
//        return "/0_test";
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
