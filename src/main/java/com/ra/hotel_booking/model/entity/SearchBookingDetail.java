package com.ra.hotel_booking.model.entity;

import com.ra.hotel_booking.model.entity.constants.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SearchBookingDetail {

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus; // Trạng thái đặt phòng

    private String searchKey; //name customer, roomNumber

    public SearchBookingDetail() {
        this.bookingStatus = null;
        this.searchKey = "";
    }
}
