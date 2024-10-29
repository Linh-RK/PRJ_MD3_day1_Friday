package com.ra.hotel_booking.model.dao.admin.room;

import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.SearchBooking;

import java.util.List;
public interface RoomDAO {
    List<Room> findAll();
    Integer create(Room room);
    Room findById(int id);
    Boolean update(Room room);
    void delete(int id);

    int totalElement(Search search);
    List<Room> findAllPerPage(int page,Search search);
    int totalPages(Search search);
    boolean existsByRoomNumber(Integer roomNumber);
//    -----------------
    int totalElement(SearchBooking searchBooking);
    List<Room> findAll(SearchBooking searchBooking);
    int totalPages(SearchBooking searchBooking);

    boolean isAvailble(int roomId, SearchBooking searchBooking);
}
