package com.ra.hotel_booking.model.entity;

import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
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
    private int pageSize = 6;
    private RoomTypeName roomType = null;
    private AvailabilityStatus availabilityStatus = null;
    private String sort = null;
    private Double priceMin = 0.0;
    private Double priceMax;
}


