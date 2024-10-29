package com.ra.hotel_booking.model.entity;

import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class SearchBooking {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer adults;
    private Integer children;
    private RoomTypeName roomType;

    public SearchBooking() {
        this.startDate = null;
        this.endDate = null;
        this.adults = 1;
        this.children = 0;
        this.roomType = null;
    }
}
