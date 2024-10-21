package com.ra.hotel_booking.model.service.admin.booking;

import com.ra.hotel_booking.model.entity.Booking;
import com.ra.hotel_booking.model.entity.Search;

import java.util.List;

public interface BookingService {
    List<Booking> findAll();
    Boolean create(Booking booking);
    Booking findById(int id);
    Boolean update(Booking booking);
    void delete(int id);
    int totalElement(Search search);
    List<Booking> findAllPerPage(int page,Search search);
    int totalPages(Search search);
}
