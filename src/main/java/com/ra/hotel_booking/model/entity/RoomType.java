//package com.ra.hotel_booking.model.entity;
//
//import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class RoomType {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Enumerated(EnumType.STRING)
//    private RoomTypeName typeName;
//
//    private Double price; // Trường giá
//
//    @ElementCollection
//    private List<String> amenities;
//
//    @ElementCollection
//    private List<String> houseRules; // Thêm trường quy định nhà
//
//    public RoomType() {}
//
//    public RoomType(RoomTypeName typeName, Double price) {
//        this.typeName = typeName;
//        this.price = price;
//        this.amenities = setAmenitiesByRoomType(typeName);
//        this.houseRules = setHouseRulesByRoomType(typeName); // Khởi tạo house rules
//    }
//
//    // Getter and Setter
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public RoomTypeName getTypeName() {
//        return typeName;
//    }
//
//    public void setTypeName(RoomTypeName typeName) {
//        this.typeName = typeName;
//        this.amenities = setAmenitiesByRoomType(typeName); // Cập nhật tiện nghi khi thay đổi loại phòng
//        this.houseRules = setHouseRulesByRoomType(typeName); // Cập nhật house rules khi thay đổi loại phòng
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public List<String> getAmenities() {
//        return amenities;
//    }
//
//    public void setAmenities(List<String> amenities) {
//        this.amenities = amenities;
//    }
//
//    public List<String> getHouseRules() {
//        return houseRules;
//    }
//
//    public void setHouseRules(List<String> houseRules) {
//        this.houseRules = houseRules;
//    }
//
//    // Method to set amenities based on room type
//    private List<String> setAmenitiesByRoomType(RoomTypeName roomType) {
//        List<String> amenities = new ArrayList<>();
//
//        switch (roomType) {
//            case SINGLE_ROOM:
//                amenities.add("<i class='fas fa-bed'></i> Single Bed");
//                amenities.add("<i class='fas fa-desktop'></i> Work Desk");
//                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
//                amenities.add("<i class='fas fa-lock'></i> Safe");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-shower'></i> Private Bathroom (shower or bathtub)");
//                break;
//
//            case DOUBLE_ROOM:
//                amenities.add("<i class='fas fa-bed'></i> Double Bed or Two Single Beds");
//                amenities.add("<i class='fas fa-wardrobe'></i> Wardrobe");
//                amenities.add("<i class='fas fa-desktop'></i> Work Desk");
//                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-shower'></i> Private Bathroom");
//                break;
//
//            case FAMILY_ROOM:
//                amenities.add("<i class='fas fa-bed'></i> Double Bed and Single Bed (or Bunk Bed)");
//                amenities.add("<i class='fas fa-users'></i> Living Area");
//                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-fridge'></i> Refrigerator");
//                amenities.add("<i class='fas fa-shower'></i> Private Bathroom");
//                break;
//
//            case SUITE:
//                amenities.add("<i class='fas fa-couch'></i> Private Living Area");
//                amenities.add("<i class='fas fa-bed'></i> King-size Bed");
//                amenities.add("<i class='fas fa-concierge-bell'></i> Small Kitchen or Cooking Area");
//                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-bath'></i> Spa Bathtub or Shower");
//                break;
//
//            case EXECUTIVE_ROOM:
//                amenities.add("<i class='fas fa-bed'></i> King-size Bed");
//                amenities.add("<i class='fas fa-desktop'></i> Large Work Desk");
//                amenities.add("<i class='fas fa-lock'></i> Safe");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-coffee'></i> Complimentary Drinks in Room");
//                amenities.add("<i class='fas fa-bath'></i> Luxurious Bathroom");
//                break;
//
//            case APARTMENT_ROOM:
//                amenities.add("<i class='fas fa-door-open'></i> One or Two Bedrooms");
//                amenities.add("<i class='fas fa-utensils'></i> Fully Equipped Kitchen");
//                amenities.add("<i class='fas fa-users'></i> Living Area");
//                amenities.add("<i class='fas fa-tshirt'></i> In-room Laundry");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-shower'></i> Private Bathroom");
//                break;
//
//            case DELUXE_ROOM:
//                amenities.add("<i class='fas fa-bed'></i> King-size or Double Bed");
//                amenities.add("<i class='fas fa-paint-brush'></i> Premium Decor");
//                amenities.add("<i class='fas fa-tv'></i> Flat-screen TV");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-glass-cheers'></i> Welcome Drinks");
//                amenities.add("<i class='fas fa-bath'></i> Luxurious Bathroom with Premium Amenities");
//                break;
//
//            case PENTHOUSE_ROOM:
//                amenities.add("<i class='fas fa-mountain'></i> Stunning Views");
//                amenities.add("<i class='fas fa-couch'></i> Spacious Living Area");
//                amenities.add("<i class='fas fa-concierge-bell'></i> Small Kitchen");
//                amenities.add("<i class='fas fa-balcony'></i> Private Balcony");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                amenities.add("<i class='fas fa-bath'></i> Luxurious Bathroom with Large Bathtub");
//                break;
//
//            case VIP_ROOM:
//                amenities.add("<i class='fas fa-user-secret'></i> Private Concierge Service");
//                amenities.add("<i class='fas fa-glass-cheers'></i> Complimentary Welcome Drinks");
//                amenities.add("<i class='fas fa-star'></i> Premium Amenities");
//                amenities.add("<i class='fas fa-bath'></i> Spa Bathtub");
//                amenities.add("<i class='fas fa-wifi'></i> Free Wi-Fi");
//                break;
//
//            default:
//                break;
//        }
//        return amenities;
//    }
//
//    // Method to set house rules based on room type
//    private List<String> setHouseRulesByRoomType(RoomTypeName roomType) {
//        List<String> houseRules = new ArrayList<>();
//
//        switch (roomType) {
//            case SINGLE_ROOM:
//                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
//                houseRules.add("<i class='fas fa-birthday-cake'></i> No parties allowed.");
//                houseRules.add("<i class='fas fa-volume-mute'></i> Please keep noise to a minimum after 10 PM.");
//                break;
//
//            case DOUBLE_ROOM:
//                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
//                houseRules.add("<i class='fas fa-trash'></i> Please keep the room clean.");
//                houseRules.add("<i class='fas fa-birthday-cake'></i> No parties allowed.");
//                break;
//
//            case FAMILY_ROOM:
//                houseRules.add("<i class='fas fa-child'></i> Children must be supervised by an adult.");
//                houseRules.add("<i class='fas fa-paw'></i> No pets allowed in the room.");
//                houseRules.add("<i class='fas fa-volume-mute'></i> Please keep noise to a minimum after 10 PM.");
//                break;
//
//            case SUITE:
//                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
//                houseRules.add("<i class='fas fa-users'></i> Maximum guest limit applies.");
//                houseRules.add("<i class='fas fa-trash'></i> Please keep the room clean.");
//                break;
//
//            case EXECUTIVE_ROOM:
//                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
//                houseRules.add("<i class='fas fa-id-card'></i> Guests are required to present identification.");
//                break;
//
//            case APARTMENT_ROOM:
//                houseRules.add("<i class='fas fa-clock'></i> Flexible check-in and check-out times.");
//                houseRules.add("<i class='fas fa-birthday-cake'></i> No parties allowed.");
//                break;
//
//            case DELUXE_ROOM:
//                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
//                houseRules.add("<i class='fas fa-volume-mute'></i> Please keep noise to a minimum after 10 PM.");
//                break;
//
//            case PENTHOUSE_ROOM:
//                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
//                houseRules.add("<i class='fas fa-birthday-cake'></i> Parties may be allowed with management's consent.");
//                break;
//
//            case VIP_ROOM:
//                houseRules.add("<i class='fas fa-smoking-ban'></i> No smoking in the room.");
//                houseRules.add("<i class='fas fa-star'></i> VIP guests must book in advance.");
//                break;
//
//            default:
//                break;
//        }
//        return houseRules;
//    }
//
//}
