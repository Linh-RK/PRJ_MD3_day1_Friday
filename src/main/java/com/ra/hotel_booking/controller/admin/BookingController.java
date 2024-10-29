package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.entity.*;
import com.ra.hotel_booking.model.entity.constants.BookingStatus;
import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
import com.ra.hotel_booking.model.service.admin.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    private BookingStatus bookingStatus;
    public int page;
    public int totalPages;
    public static List<BookingStatus> bookingStatusList = Arrays.asList(BookingStatus.values());
    @GetMapping
    public String getBookings(@ModelAttribute("searchBookingDetail") SearchBookingDetail searchBookingDetail,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                              @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                              Model model) {
        List<Booking> bookingList;
        if(searchBookingDetail.getSearchKey() == null){
            searchBookingDetail.setSearchKey("");
        }
        totalPages = bookingService.totalPages(searchBookingDetail);

        if (page + 1 > totalPages || page < 0) {
            page = totalPages - 1;
        }

        bookingList = bookingService.findAllPerPage(page,sortField,sortDir, searchBookingDetail);
        System.out.println(bookingList.size());
        System.out.println(bookingList.get(0).getBookingId());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("bookingStatusList", bookingStatusList);
        model.addAttribute("bookings", bookingList);
        model.addAttribute("searchBookingDetail", searchBookingDetail);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        return "/admin/booking/index";
    }

    @GetMapping("/update/{id}")
    public String updateBooking(
            @PathVariable("id") int id, Model model) {
        System.out.println(id);
        Booking booking = bookingService.findById(id);
        switch (booking.getBookingStatus()){
            case PENDING:{
                booking.setBookingStatus(BookingStatus.CONFIRMED);
                bookingService.update(booking);
                break;
            }
            case CONFIRMED:{
                booking.setBookingStatus(BookingStatus.CHECKED_IN);
                bookingService.update(booking);
                break;
            }
            case CHECKED_IN:{
                booking.setBookingStatus(BookingStatus.CLEANING);
                bookingService.update(booking);
                break;
            }
            case CLEANING:{
                booking.setBookingStatus(BookingStatus.DONE);
                bookingService.update(booking);
                break;
            }
        }

        return "redirect:/admin/booking/";
    }
    @GetMapping("/cancel/{id}")
    public String cancelBooking( @PathVariable("id") int id, Model model) {
        Booking booking = bookingService.findById(id);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingService.update(booking);
        return "redirect:/admin/booking/";
    }
}
