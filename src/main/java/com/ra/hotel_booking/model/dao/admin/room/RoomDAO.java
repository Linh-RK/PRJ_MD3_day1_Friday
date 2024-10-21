package com.ra.hotel_booking.model.dao.admin.room;

import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;

import java.util.List;
public interface RoomDAO {
    List<Room> findAll();
    Boolean create(Room room);
    Room findById(int id);
    Boolean update(Room room);
    void delete(int id);
    int totalElement(Search search);
    List<Room> findAllPerPage(int page,Search search);
    int totalPages(Search search);
    boolean existsByRoomNumber(Integer roomNumber);
}
