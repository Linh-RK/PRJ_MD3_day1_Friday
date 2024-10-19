package com.ra.hotel_booking.model.service.admin.room;

import com.ra.hotel_booking.model.dao.admin.RoomDAO;
import com.ra.hotel_booking.model.entity.DTO.RoomDTO;
import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomType;
import com.ra.hotel_booking.model.service.UploadFile.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private UploadFileService uploadFileService;
    @Override
    public List<Room> findAll() {
        return roomDAO.findAll();
    }

    @Override
    public Boolean create(RoomDTO roomDTO) {
        String image = "";
        if (roomDTO.getImagedd() != null) {
            image = uploadFileService.uploadFile(roomDTO.getImagedd());
            System.out.println(image);
        }
//        upload file
//        convert
        Room roomEntity = new Room();
        roomEntity.setRoomNumber(roomDTO.getRoomNumber());
        roomEntity.setRoomType(roomDTO.getRoomType());
        roomEntity.setPricePerNight(roomDTO.getPricePerNight());
        roomEntity.setAvailabilityStatus(roomDTO.getAvailabilityStatus());
        roomEntity.setImage(image);
        roomEntity.setDescription(roomDTO.getDescription());
        return roomDAO.create(roomEntity);
    }
    @Override
    public Room findById(int id) {
        return roomDAO.findById(id);
    }

    @Override
    public Boolean update(Room room) {

        return roomDAO.update(room);
    }

    @Override
    public void delete(int id) {
        roomDAO.delete(id);
    }

    @Override
    public Double maxPrice() {
        return roomDAO.findAll().stream().map(Room::getPricePerNight).max(Comparator.comparingDouble(Double::doubleValue)).orElse(0.0);
    }

    @Override
    public int totalElement(Search search) {
        return roomDAO.totalElement(search);
    }

    @Override
    public List<Room> findAllPerPage(int page, Search search) {
        return roomDAO.findAllPerPage(page, search);
    }

    @Override
    public int totalPages(Search search) {
        return roomDAO.totalPages(search);
    }


}
