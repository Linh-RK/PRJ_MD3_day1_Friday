package com.ra.hotel_booking.model.service.admin.booking;

import com.ra.hotel_booking.model.entity.Booking;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.SearchBookingDetail;

import java.util.List;

public interface BookingService {
    List<Booking> findAll();
    Boolean create(Booking booking);
    Booking findById(int id);
    Boolean update(Booking booking);
    void delete(int id);

    //    ----------------------------------
    int totalElement(SearchBookingDetail searchBookingDetail);
    List<Booking> findAllPerPage(int page, String sortField, String sortDir,SearchBookingDetail searchBookingDetail);
    int totalPages(SearchBookingDetail searchBookingDetail);

    //    ----------------------------------
    void updateBookingStatuses();

    //
    int totalElementByCustomer(int customerId,SearchBookingDetail searchBookingDetail);
    List<Booking> findAllPerPageByCustomer(int customerId,int page, String sortField, String sortDir,SearchBookingDetail searchBookingDetail);
    int totalPagesByCustomer(int customerId,SearchBookingDetail searchBookingDetail);

}
