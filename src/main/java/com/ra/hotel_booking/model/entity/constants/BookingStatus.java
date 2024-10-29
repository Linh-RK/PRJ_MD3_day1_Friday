package com.ra.hotel_booking.model.entity.constants;

public enum BookingStatus {
    PENDING, //đang đơi admin xác nhận
    CANCELLED,// khách hàng huỷ hoặc admin huỷ
    CONFIRMED,// admin confirm
    CLEANING,//tự động chuyển sau ngày checkout và sau 1 ngày sẽ chuyển thành
    CHECKED_IN,//tự động chuyển khi đến ngày
//    CHECKED_OUT,//tự động chuyển khi đến ngày
    DONE //tự động chuyển khi khi hết ngày cleaning
//    NO_SHOW,
//    RESERVED
}
