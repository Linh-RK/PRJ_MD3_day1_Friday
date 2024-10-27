package com.ra.hotel_booking.model.dao.admin.room;

import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.RoomImages;

import java.util.List;

public interface RoomImagesDAO {
    void delete(int id);
    Boolean add(RoomImages roomImages);
    List<RoomImages> findByRoomId(int id);
    List<RoomImages> findAll();
    void delete(String name,int id);
}
