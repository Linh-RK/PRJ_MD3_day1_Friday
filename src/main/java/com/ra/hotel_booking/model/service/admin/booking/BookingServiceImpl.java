package com.ra.hotel_booking.model.service.admin.booking;

import com.ra.hotel_booking.model.dao.user.customer.booking.BookingDAO;
import com.ra.hotel_booking.model.entity.Booking;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.SearchBookingDetail;
import com.ra.hotel_booking.model.entity.constants.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Sort;

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
    public int totalElement(SearchBookingDetail searchBookingDetail) {
        return bookingDAO.totalElement(searchBookingDetail);
    }

    @Override
    public List<Booking> findAllPerPage(int page, String sortField, String sortDir,SearchBookingDetail searchBookingDetail) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return bookingDAO.findAllPerPage(page,sortField, sortDir, searchBookingDetail);
    }

    @Override
    public int totalPages(SearchBookingDetail searchBookingDetail) {
        return bookingDAO.totalPages(searchBookingDetail);
    }

    @Override
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "*/10 * * * * ?")
    public void updateBookingStatuses() {
        List<Booking> bookings = bookingDAO.findAll(); // Hoặc tìm kiếm theo ngày
        LocalDate today = LocalDate.now();

        for (Booking booking : bookings) {
            if (booking.getCheckOut().isBefore(today)) {
                booking.setBookingStatus(BookingStatus.CLEANING);
            } else if (booking.getCheckIn().isEqual(today)) {
                booking.setBookingStatus(BookingStatus.CHECKED_IN);
            } else if (booking.getBookingStatus() == BookingStatus.CLEANING ) {
                booking.setBookingStatus(BookingStatus.DONE);
            }
            // Cập nhật booking
            bookingDAO.update(booking);
        }
    }

//    -----------------------------------
    @Override
    public int totalElementByCustomer(int customerId, SearchBookingDetail searchBookingDetail) {
        return bookingDAO.totalElementByCustomer(customerId, searchBookingDetail);
    }

    @Override
    public List<Booking> findAllPerPageByCustomer(int customerId, int page, String sortField, String sortDir, SearchBookingDetail searchBookingDetail) {
        return bookingDAO.findAllPerPageByCustomer(customerId, page, sortField, sortDir, searchBookingDetail);
    }

    @Override
    public int totalPagesByCustomer(int customerId, SearchBookingDetail searchBookingDetail) {
        return bookingDAO.totalPagesByCustomer(customerId, searchBookingDetail);
    }
}
