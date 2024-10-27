package com.ra.hotel_booking.model.service.admin.room;

import com.ra.hotel_booking.model.dao.admin.room.RoomDAO;
import com.ra.hotel_booking.model.dao.admin.room.RoomImagesDAO;
import com.ra.hotel_booking.model.entity.DTO.RoomDTO;
import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.RoomImages;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.service.UploadFile.FileService;
import com.ra.hotel_booking.model.service.UploadFile.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private RoomImagesDAO roomImagesDAO;
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private FileService fileService;
    @Autowired
    private RoomImagesService roomImagesService;
    @Override
    public List<Room> findAll() {
        return roomDAO.findAll();
    }

    @Override
    public Boolean create(RoomDTO roomDTO) {
        //them moi room
//        Room room = Room.builder()
//                .roomNumber(roomDTO.getRoomNumber())
//                .roomType(roomDTO.getRoomType())
//                .pricePerNight(setPricePerNightByRoomType(roomDTO.getRoomType()))
//                .availabilityStatus(roomDTO.getAvailabilityStatus())
//                .description(roomDTO.getDescription())
//                .build();
        Room room = new Room
                (roomDTO.getRoomNumber(),roomDTO.getRoomType(),
                roomDTO.getAvailabilityStatus(),roomDTO.getDescription(),
                uploadFileService.uploadFile(roomDTO.getImageTitle()));
        System.out.println(room.getRoomType());

        int id = roomDAO.create(room);
        if(id<0){
            return false;
        }
        System.out.println(id);
        List<RoomImages> roomImagesList = new ArrayList<>();
        List<String> imagesDisplay = new ArrayList<>();
        for (MultipartFile file : roomDTO.getImageList()) {
            if (!file.isEmpty()) {
                String imageName = uploadFileService.uploadFile(file);  // Lưu file và trả về URL
                RoomImages roomImage= RoomImages.builder()
                        .room(roomDAO.findById(id))
                        .image(imageName)
                        .build();
                roomImagesList.add(roomImage);
                roomImagesDAO.add(roomImage);

                imagesDisplay.add(imageName);
            }
        }
       // chay vong lap
        return true;
    }
    @Override
    public Room findById(int id) {
        return roomDAO.findById(id);
    }

    @Override
    public Boolean update(RoomDTO roomDTO,int roomId) {
    Room room = roomDAO.findById(roomId);
    if(!roomDTO.getDeleteImage().isEmpty()){
//         Xóa các ảnh đã chọn
        for (Integer imageId : roomDTO.getDeleteImage()) {
            roomImagesDAO.delete(imageId);
        }
    }
        List<RoomImages> roomImagesList = room.getImages();
        if(!roomDTO.getImageList().isEmpty()){
           // Thêm các ảnh mới
           List<String> imagesDisplay = new ArrayList<>();
           for (MultipartFile file : roomDTO.getImageList()) {
               if (!file.isEmpty()) {
                   String imageName = uploadFileService.uploadFile(file);  // Lưu file và trả về URL
                   RoomImages roomImage= RoomImages.builder()
                           .room(roomDAO.findById(roomId))
                           .image(imageName)
                           .build();
                   roomImagesList.add(roomImage);
                   roomImagesDAO.add(roomImage);
//                   imagesDisplay.add(imageName);
               }
           }
        }

RoomDTO roomDTO1 = new RoomDTO(roomDTO.getRoomNumber(), roomDTO.getRoomType(),roomDTO.getAvailabilityStatus(), roomDTO.getDescription());
    room.setImages(roomImagesList);

    room.setRoomNumber(roomDTO.getRoomNumber());
    room.setRoomType(roomDTO.getRoomType());
    room.setAvailabilityStatus(roomDTO.getAvailabilityStatus());
    room.setDescription(roomDTO.getDescription());
    room.setPricePerNight(roomDTO1.getPricePerNight());
    room.setAmenities(roomDTO1.getAmenities());
    room.setHouseRules(roomDTO1.getHouseRules());
    if(!Objects.requireNonNull(roomDTO.getImageTitle().getOriginalFilename()).isEmpty()){
        String imageTitle = uploadFileService.uploadFile(roomDTO.getImageTitle());
        room.setImageTitle(imageTitle);
    }
        return roomDAO.update(room);
//        return true;
    }

    @Override
    public void delete(int id) {
        roomDAO.delete(id);
    }

    @Override
    public Double maxPrice() {
        return roomDAO.findAll().stream()
                .map(Room::getPricePerNight)
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Double::doubleValue)).orElse(0.0);
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
