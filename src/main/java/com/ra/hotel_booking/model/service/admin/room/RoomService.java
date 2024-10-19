package com.ra.hotel_booking.model.service.admin.room;

import com.ra.hotel_booking.model.entity.DTO.RoomDTO;
import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;

import java.util.List;

public interface RoomService {
    List<Room> findAll();
    Boolean create(RoomDTO roomDTO);
    Room findById(int id);
    Boolean update(Room room);
    void delete(int id);

    Double maxPrice();
    int totalElement(Search search);
    List<Room> findAllPerPage(int page,Search search);
    int totalPages(Search search);
}
