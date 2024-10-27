package com.ra.hotel_booking.model.service.UploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    private ServletContext servletContext;

    @Override
    public String uploadFile(MultipartFile image) {
        String uploadPath = "/Users/phamlinh/Desktop/HOTEL_BOOKING/src/main/webapp/uploads";
        File uploadDir = new File(uploadPath);

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Lấy tên tệp gốc và kiểm tra xem có rỗng không
        String fileImage = image.getOriginalFilename();
        if (fileImage == null || fileImage.isEmpty()) {
            throw new RuntimeException("Tên tệp không hợp lệ.");
        }

        // Đảm bảo tệp đích được tạo chính xác
        File destinationFile = new File(uploadDir, fileImage);

        try {
            // Thực hiện sao chép tệp
            FileCopyUtils.copy(image.getBytes(), destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("Không thể sao chép tệp: " + e.getMessage(), e);
        }

        return fileImage;
    }
}
//@Service
//public class UploadFileServiceImpl implements UploadFileService {
//    @Autowired
//    private ServletContext servletContext;
//    @Override
//    public String uploadFile(MultipartFile image) {
//        String uploadPath = "/Users/phamlinh/Desktop/HOTEL_BOOKING/src/main/webapp/uploads";
//        File file = new File(uploadPath);
//        if(!file.exists()){
//            file.mkdir();
//        }
//        String fileImage = image.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(image.getBytes(),new File(uploadPath+File.separator+fileImage));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return fileImage;
////
//    }
//}
