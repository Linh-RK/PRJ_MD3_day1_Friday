package com.ra.hotel_booking.model.entity;

import com.google.protobuf.Internal;
import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomType;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private String searchKey = "";
    private int pageSize = 5;
    private RoomType roomType = null;
    private AvailabilityStatus availabilityStatus = null;
    private String sort = "ASC";
    private Double priceMin = 0.0;
    private Double priceMax;
}


