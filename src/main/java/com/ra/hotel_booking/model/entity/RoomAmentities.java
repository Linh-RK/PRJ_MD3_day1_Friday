//package com.ra.hotel_booking.model.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Builder
//@Table(name = "amenities")
//public class RoomAmentities {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "room_id",referencedColumnName = "room_id")
//    private Room room;
//
//    @Column(name = "room_number", nullable = false)
//    private Integer roomNumber;
//
//}
