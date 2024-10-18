package com.ra.hotel_booking.model.service.UploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    private ServletContext servletContext;
    @Override
    public String uploadFile(MultipartFile image) {
        String uploadPath = "/Users/phamlinh/Desktop/HOTEL_BOOKING/src/main/webapp/uploads";
        File file = new File(uploadPath);
        if(!file.exists()){
            file.mkdir();
        }
        String fileImage = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(),new File(uploadPath+File.separator+fileImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileImage;
    }
}
