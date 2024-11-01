package com.ra.hotel_booking.model.entity;

import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomTypeName roomType;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false)
    private AvailabilityStatus availabilityStatus;

    @Column(name = "description", nullable = false)
    private String description;

   @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
   @Fetch(FetchMode.SUBSELECT)
   private List<RoomImages> images = new ArrayList<>();

    private  String imageTitle;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> amenities;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> houseRules; // Thêm trường quy định nhà

    private int maxAdult;

    private int maxChildren;

    public Room() {
    }

    public Room(Integer roomNumber, RoomTypeName roomType, AvailabilityStatus availabilityStatus, String description, String imageTitle, int maxAdult, int maxChildren) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = setPricePerNightByRoomType(roomType);
        this.availabilityStatus = availabilityStatus;
        this.description = description;
        this.amenities = setAmenitiesByRoomType(roomType);
        this.houseRules = setHouseRulesByRoomType(roomType);
        this.imageTitle = imageTitle;
        this.maxAdult = maxAdult;
        this.maxChildren = maxChildren;
    }


    public void setTypeName(RoomTypeName roomType) {
        this.roomType = roomType;
        this.amenities = setAmenitiesByRoomType(roomType); // Cập nhật tiện nghi khi thay đổi loại phòng
        this.houseRules = setHouseRulesByRoomType(roomType);// Cập nhật house rules khi thay đổi loại phòng
        this.pricePerNight = setPricePerNightByRoomType(roomType); // Cập nhật giá khi thay đổi loại phòng
    }

    // Method to set amenities based on room type
    private Double setPricePerNightByRoomType(RoomTypeName roomType) {
        double pricePerNight = 0.0;

        switch (roomType) {
            case SINGLE_ROOM:
                pricePerNight = 500000.0;
                break;

            case DOUBLE_ROOM:
                pricePerNight = 600000.0;
                break;

            case FAMILY_ROOM:
                pricePerNight = 700000.0;
                break;

            case SUITE:
                pricePerNight = 800000.0;
                break;

            case EXECUTIVE_ROOM:
                pricePerNight = 900000.0;
                break;

            case APARTMENT_ROOM:
                pricePerNight = 1000000.0;
                break;

            case DELUXE_ROOM:
                pricePerNight = 1500000.0;
                break;

            case PENTHOUSE_ROOM:
                pricePerNight = 2000000.0;
                break;

            case VIP_ROOM:
                pricePerNight = 3000000.0;
                break;

            default:
                break;
        }
        return pricePerNight;
    }

    // Method to set amenities based on room type
    private List<String> setAmenitiesByRoomType(RoomTypeName roomType) {
        List<String> amenities = new ArrayList<>();

        switch (roomType) {
            case SINGLE_ROOM:
                amenities.add("<i class='fas fa-bed'></i> Single Bed");
                amenities.add("<i class='fas fa-desktop'></i> Work Desk");
                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
                amenities.add("<i class='fas fa-lock'></i> Safe");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-shower'></i> Private Bathroom (shower or bathtub)");
                break;

            case DOUBLE_ROOM:
                amenities.add("<i class='fas fa-bed'></i> Double Bed or Two Single Beds");
                amenities.add("<i class='fas fa-wardrobe'></i> Wardrobe");
                amenities.add("<i class='fas fa-desktop'></i> Work Desk");
                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-shower'></i> Private Bathroom");
                break;

            case FAMILY_ROOM:
                amenities.add("<i class='fas fa-bed'></i> Double Bed and Single Bed (or Bunk Bed)");
                amenities.add("<i class='fas fa-users'></i> Living Area");
                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-fridge'></i> Refrigerator");
                amenities.add("<i class='fas fa-shower'></i> Private Bathroom");
                break;

            case SUITE:
                amenities.add("<i class='fas fa-couch'></i> Private Living Area");
                amenities.add("<i class='fas fa-bed'></i> King-size Bed");
                amenities.add("<i class='fas fa-concierge-bell'></i> Small Kitchen or Cooking Area");
                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-bath'></i> Spa Bathtub or Shower");
                break;

            case EXECUTIVE_ROOM:
                amenities.add("<i class='fas fa-bed'></i> King-size Bed");
                amenities.add("<i class='fas fa-desktop'></i> Large Work Desk");
                amenities.add("<i class='fas fa-lock'></i> Safe");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-coffee'></i> Complimentary Drinks in Room");
                amenities.add("<i class='fas fa-bath'></i> Luxurious Bathroom");
                break;

            case APARTMENT_ROOM:
                amenities.add("<i class='fas fa-door-open'></i> One or Two Bedrooms");
                amenities.add("<i class='fas fa-utensils'></i> Fully Equipped Kitchen");
                amenities.add("<i class='fas fa-users'></i> Living Area");
                amenities.add("<i class='fas fa-tshirt'></i> In-room Laundry");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-shower'></i> Private Bathroom");
                break;

            case DELUXE_ROOM:
                amenities.add("<i class='fas fa-bed'></i> King-size or Double Bed");
                amenities.add("<i class='fas fa-paint-brush'></i> Premium Decor");
                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-glass-cheers'></i> Welcome Drinks");
                amenities.add("<i class='fas fa-bath'></i> Luxurious Bathroom with Premium Amenities");
                break;

            case PENTHOUSE_ROOM:
                amenities.add("<i class='fas fa-mountain'></i> Stunning Views");
                amenities.add("<i class='fas fa-couch'></i> Spacious Living Area");
                amenities.add("<i class='fas fa-concierge-bell'></i> Small Kitchen");
                amenities.add("<i class='fas fa-balcony'></i> Private Balcony");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                amenities.add("<i class='fas fa-bath'></i> Luxurious Bathroom with Large Bathtub");
                break;

            case VIP_ROOM:
                amenities.add("<i class='fas fa-user-secret'></i> Private Concierge Service");
                amenities.add("<i class='fas fa-glass-cheers'></i> Complimentary Welcome Drinks");
                amenities.add("<i class='fas fa-star'></i> Premium Amenities");
                amenities.add("<i class='fas fa-bath'></i> Spa Bathtub");
                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
                break;

            default:
                break;
        }
        return amenities;
    }

    // Method to set house rules based on room type
    private List<String> setHouseRulesByRoomType(RoomTypeName roomType) {
        List<String> houseRules = new ArrayList<>();

        switch (roomType) {
            case SINGLE_ROOM:
                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
                houseRules.add("<i class='fas fa-birthday-cake'></i> No parties allowed.");
                houseRules.add("<i class='fas fa-volume-mute'></i> Please keep noise to a minimum after 10 PM.");
                break;

            case DOUBLE_ROOM:
                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
                houseRules.add("<i class='fas fa-trash'></i> Please keep the room clean.");
                houseRules.add("<i class='fas fa-birthday-cake'></i> No parties allowed.");
                break;

            case FAMILY_ROOM:
                houseRules.add("<i class='fas fa-child'></i> Children must be supervised by an adult.");
                houseRules.add("<i class='fas fa-paw'></i> No pets allowed in the room.");
                houseRules.add("<i class='fas fa-volume-mute'></i> Please keep noise to a minimum after 10 PM.");
                break;

            case SUITE:
                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
                houseRules.add("<i class='fas fa-users'></i> Maximum guest limit applies.");
                houseRules.add("<i class='fas fa-trash'></i> Please keep the room clean.");
                break;

            case EXECUTIVE_ROOM:
                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
                houseRules.add("<i class='fas fa-id-card'></i> Guests are required to present identification.");
                break;

            case APARTMENT_ROOM:
                houseRules.add("<i class='fas fa-clock'></i> Flexible check-in and check-out times.");
                houseRules.add("<i class='fas fa-birthday-cake'></i> No parties allowed.");
                break;

            case DELUXE_ROOM:
                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
                houseRules.add("<i class='fas fa-volume-mute'></i> Please keep noise to a minimum after 10 PM.");
                break;

            case PENTHOUSE_ROOM:
                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
                houseRules.add("<i class='fas fa-birthday-cake'></i> Parties may be allowed with management's consent.");
                break;

            case VIP_ROOM:
                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
                houseRules.add("<i class='fas fa-star'></i> VIP guests must book in advance.");
                break;

            default:
                break;
        }
        return houseRules;
    }
}
