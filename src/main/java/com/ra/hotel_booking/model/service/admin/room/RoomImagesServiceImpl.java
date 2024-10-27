package com.ra.hotel_booking.model.service.admin.room;

import com.ra.hotel_booking.model.dao.admin.room.RoomImagesDAO;
import com.ra.hotel_booking.model.entity.RoomImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoomImagesServiceImpl implements RoomImagesService {
    @Autowired
    private RoomImagesDAO roomImagesDAO;

    @Override
    public void delete(int id) {
        roomImagesDAO.delete(id);
    }

    @Override
    public Boolean add(RoomImages roomImages) {
        roomImagesDAO.add(roomImages);
        return null;
    }

    @Override
    public List<RoomImages> findByRoomId(int id) {
        return roomImagesDAO.findByRoomId(id);
    }

    @Override
    public List<RoomImages> findAll() {
        return roomImagesDAO.findAll();
    }

    @Override
    public void delete(String name,int id) {
        roomImagesDAO.delete(name,id);
    }


}
