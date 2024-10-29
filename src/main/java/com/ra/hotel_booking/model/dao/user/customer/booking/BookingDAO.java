package com.ra.hotel_booking.model.dao.user.customer.booking;

import com.ra.hotel_booking.model.entity.Booking;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.SearchBookingDetail;
import com.ra.hotel_booking.model.entity.constants.BookingStatus;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO {
    List<Booking> findAll();
    Boolean create(Booking booking);
    Booking findById(int id);
    Boolean update(Booking booking);
    void delete(int id);

    //Search
    int totalElement(SearchBookingDetail searchBookingDetail);
    List<Booking> findAllPerPage(int page, String sortField, String sortDir,SearchBookingDetail searchBookingDetail);
    int totalPages(SearchBookingDetail searchBookingDetail);

    //Autoupdate status
    void saveAll(List<Booking> checkInBookings);
    List<Booking> findAllByCheckOutDateAndStatus(LocalDate today, BookingStatus bookingStatus);
    List<Booking> findAllByCheckInDateAndStatus(LocalDate today, BookingStatus bookingStatus);

    //booking history
    int totalElementByCustomer(int customerId,SearchBookingDetail searchBookingDetail);
    List<Booking> findAllPerPageByCustomer(int customerId,int page, String sortField, String sortDir,SearchBookingDetail searchBookingDetail);
    int totalPagesByCustomer(int customerId,SearchBookingDetail searchBookingDetail);

}
