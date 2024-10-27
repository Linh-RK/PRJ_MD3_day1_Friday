package com.ra.hotel_booking.model.service.UploadFile;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class FileService {

    private static final String UPLOAD_DIR = "/Users/phamlinh/Desktop/HOTEL_BOOKING/src/main/webapp/uploads/";

    public void deleteImageFromFileSystem(String imageName) {
        Path filePath = Paths.get(UPLOAD_DIR + imageName);
        try {
            Files.deleteIfExists(filePath); // Xóa tệp nếu nó tồn tại
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
