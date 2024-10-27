package com.ra.hotel_booking.model.entity;

import com.ra.hotel_booking.model.service.UploadFile.UploadFileService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room_images")
public class RoomImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    private int roomId;

    @Column(name = "image",nullable = false)
    private String image;

    // Quan hệ nhiều-một với Room
    @ManyToOne
    @JoinColumn(name = "room_id",referencedColumnName = "room_id")
    private Room room;
}
