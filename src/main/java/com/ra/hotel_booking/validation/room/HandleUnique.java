package com.ra.hotel_booking.validation.room;

import com.ra.hotel_booking.model.dao.admin.room.RoomDAO;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HandleUnique implements ConstraintValidator<Unique, Integer> {
    @Autowired
    private RoomDAO roomDAO;
    @Override
    public boolean isValid(Integer roomNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !roomDAO.existsByRoomNumber(roomNumber);
    }
}
