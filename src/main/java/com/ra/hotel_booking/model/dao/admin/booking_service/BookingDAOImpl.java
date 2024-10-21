package com.ra.hotel_booking.model.dao.admin.booking_service;

import com.ra.hotel_booking.model.entity.Booking;
import com.ra.hotel_booking.model.entity.Search;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
@Repository
public class BookingDAOImpl implements BookingDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Booking> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Boolean create(Booking booking) {
        return null;
    }

    @Override
    public Booking findById(int id) {
        return null;
    }

    @Override
    public Boolean update(Booking booking) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public int totalElement(Search search) {
        return 0;
    }

    @Override
    public List<Booking> findAllPerPage(int page, Search search) {
        return Collections.emptyList();
    }

    @Override
    public int totalPages(Search search) {
        return 0;
    }
}
