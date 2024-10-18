package com.ra.hotel_booking.model.entity;

import com.google.protobuf.Internal;
import com.ra.hotel_booking.model.entity.constants.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Search {
private String searchKey;
private Integer pageSize;
private RoomType roomType;
private String sort;
private Double priceMin;
private Double priceMax;
}
