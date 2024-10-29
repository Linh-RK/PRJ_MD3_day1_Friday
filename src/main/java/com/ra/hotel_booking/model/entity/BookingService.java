package com.ra.hotel_booking.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "booking_service")
public class BookingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_service_id")
    private int bookingServiceId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;  // Assuming there is an entity `Booking` in your project.

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }
}
