package com.ra.hotel_booking.model.entity.DTO;

import com.ra.hotel_booking.model.entity.Customer;
import com.ra.hotel_booking.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private LocalDate bookingDate;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int rentDate;
    private double totalPrice;
    private String status;

    private Customer customer;

    private Room room;

    private int adults;
    private int children;


//        private String paymentStatus;
//        private String paymentMethod;
//        private double deposit;

//        private String discountCode;
//        private double discountAmount;
//        private String additionalRequests;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String createdBy;
    private String lastUpdatedBy;

    public double calculateRentDetails() {
        if (checkIn != null && checkOut != null && !checkOut.isBefore(checkIn) && room != null) {
            // Tính số ngày thuê
            this.rentDate = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
            // Tính tổng tiền dựa trên số ngày thuê và giá phòng mỗi đêm
            this.totalPrice = this.rentDate * room.getPricePerNight();
            return totalPrice;
        } else {
            // Xử lý nếu thông tin không hợp lệ
            this.rentDate = 0;
            this.totalPrice = 0;
            return 0;
        }
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        calculateRentDetails();
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        calculateRentDetails();
    }

    public int getRentDate() {
        return rentDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
