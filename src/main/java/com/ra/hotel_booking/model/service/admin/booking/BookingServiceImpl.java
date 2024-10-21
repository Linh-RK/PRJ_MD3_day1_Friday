package com.ra.hotel_booking.model.service.admin.booking;

import com.ra.hotel_booking.model.dao.admin.booking_service.BookingDAO;
import com.ra.hotel_booking.model.entity.Booking;
import com.ra.hotel_booking.model.entity.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingDAO bookingDAO;

    @Override
    public List<Booking> findAll() {
        return bookingDAO.findAll();
    }

    @Override
    public Boolean create(Booking booking) {
        return bookingDAO.create(booking);
    }

    @Override
    public Booking findById(int id) {
        return bookingDAO.findById(id);
    }

    @Override
    public Boolean update(Booking booking) {
        return bookingDAO.update(booking);
    }

    @Override
    public void delete(int id) {
        bookingDAO.delete(id);
    }

    @Override
    public int totalElement(Search search) {
        return bookingDAO.totalElement(search);
    }

    @Override
    public List<Booking> findAllPerPage(int page, Search search) {
        return bookingDAO.findAllPerPage(page, search);
    }

    @Override
    public int totalPages(Search search) {
        return bookingDAO.totalPages(search);
    }
}
