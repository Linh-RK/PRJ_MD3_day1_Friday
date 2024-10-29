package com.ra.hotel_booking.model.entity;

import com.ra.hotel_booking.model.entity.constants.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
    public class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int bookingId;

        private LocalDate bookingDate;

        private LocalDate checkIn;

        private LocalDate checkOut;

        private int adults;

        private int children;

        private double totalPrice;

        private LocalDate updatedAt;

        @Enumerated(EnumType.STRING)
        @Column(name = "booking_status", nullable = false)
        private BookingStatus bookingStatus; // Trạng thái đặt phòng

        @ManyToOne
        @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
        private Customer customer;

        @ManyToOne
        @JoinColumn(name = "room_id",referencedColumnName = "room_id", nullable = false)
        private Room room;

//        private String paymentStatus;
//        private String paymentMethod;
//        private double deposit;

//        private String discountCode;
//        private double discountAmount;
//        private String additionalRequests;


    public Booking(LocalDate checkIn, LocalDate checkOut, Customer customer, Room room, int adults, int children, double totalPrice) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.customer = customer;
        this.room = room;
        this.adults = adults;
        this.children = children;
        this.totalPrice = totalPrice;
        this.bookingDate = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.bookingStatus = BookingStatus.PENDING;
    }
}
