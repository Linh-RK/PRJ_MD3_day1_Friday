package com.ra.hotel_booking.model.entity.DTO;

import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    @NotNull(message = "số phòng không được để trống")
    @Min(value = 1, message = "Số phòng phải lớn hơn 0")
    private Integer roomNumber;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Min(value = 1, message = "Giá phòng phải lớn hơn 0")
    private double pricePerNight;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    private String description;
    private MultipartFile imagedd;
}
