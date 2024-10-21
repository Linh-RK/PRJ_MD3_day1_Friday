package com.ra.hotel_booking.model.entity.constants;

public enum RoomTypeName {
    SINGLE_ROOM("Single Room"),
    DOUBLE_ROOM("Double Room"),
    FAMILY_ROOM("Family Room"),
    SUITE("Suite"),
    EXECUTIVE_ROOM("Executive Room"),
    APARTMENT_ROOM("Apartment Room"),
    DELUXE_ROOM("Deluxe Room"),
    PENTHOUSE_ROOM("Penthouse Room"),
    VIP_ROOM("VIP Room");

    private final String displayName;

    RoomTypeName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}