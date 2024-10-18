package com.ra.hotel_booking.model.service.UploadFile;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    String uploadFile(MultipartFile file);
}
