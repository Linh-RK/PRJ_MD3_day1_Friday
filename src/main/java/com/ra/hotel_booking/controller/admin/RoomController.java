package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.entity.DTO.RoomDTO;
import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.RoomImages;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
import com.ra.hotel_booking.model.service.UploadFile.UploadFileService;
import com.ra.hotel_booking.model.service.admin.room.RoomImagesService;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private RoomImagesService roomImagesService;

    public int page;
    public static String oldNameRoom = "";
    public int totalPages;
    public static List<RoomTypeName> roomTypeList = Arrays.asList(RoomTypeName.values());
    public static List<AvailabilityStatus> availabilityStatusList = Arrays.asList(AvailabilityStatus.values());


    @GetMapping
    public String index(@ModelAttribute("search") Search search,
                        @ModelAttribute("roomImages") RoomImages roomImages,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        Model model) {
        List<Room> roomList;

        if (search.getPriceMax() == null) {
            search.setPriceMax(roomService.maxPrice());
        }
        if (search.getPriceMax() == null) {
            search.setPriceMax(roomService.maxPrice());
        }
        totalPages = roomService.totalPages(search);

        if (page + 1 > totalPages || page < 0) {
            page = totalPages - 1;
        }

        roomList = roomService.findAllPerPage(page, search);
        model.addAttribute("roomImages", roomImages);
        model.addAttribute("rooms", roomList);
        model.addAttribute("search", search);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        return "/admin/index";

    }

    //    --------------------------------------------------------------------------
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("roomDTO", new RoomDTO());
        return "/admin/room/add";
    }

    @PostMapping("/add")
    public String handleAdd(Model model, @Valid @ModelAttribute("roomDTO") RoomDTO roomDTO
            , BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()
                || roomDTO.getImageList().isEmpty()
                || roomDTO.getImageList().stream().anyMatch(file -> file.getSize() <= 0)
                || roomDTO.getImageTitle().isEmpty()) {
            model.addAttribute("roomDTO", roomDTO);
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            model.addAttribute("errImg", "Please choose at least one image!");
            model.addAttribute("errImgTitle", "Please choose a image!");

            return "/admin/room/add";
        } else {
            if (roomService.create(roomDTO)) {
                return "redirect:/admin/room";
            }
            model.addAttribute("roomDTO", roomDTO);
            redirectAttributes.addFlashAttribute("errImg", "Please choose at least one image!");
            model.addAttribute("errImgTitle", "Please choose a image!");
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            return "redirect:/admin/room/add";
        }
    }

    //    --------------------------------------------------------------------------
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Room room = roomService.findById(id);
        RoomDTO roomDTO = new RoomDTO(room.getRoomNumber(), room.getRoomType(), room.getAvailabilityStatus(), room.getDescription());
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("roomDTO", roomDTO);
        model.addAttribute("room", room);
        model.addAttribute("id", id);
        return "/admin/room/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("roomDTO") RoomDTO roomDTO,
//                         @RequestParam("img") MultipartFile imageTitle,  // Ảnh tiêu đề
//                         @RequestParam("newImages") MultipartFile[] newImages,  // Ảnh chi tiết mới
//                         @RequestParam(value = "deleteImages", required = false) List<Long> deleteImageIds,  // ID của ảnh cần xóa
                         BindingResult result,
                         @PathVariable int id,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        Room room = roomService.findById(id);
//        if (result.hasErrors() ||
//                room.getImages().size()+ newImages.length - deleteImageIds.size() <= 0)
//        {
////        if (result.hasErrors() || roomDTO.getImageTitle().isEmpty() ) {
//            model.addAttribute("availabilityStatusList", availabilityStatusList);
//            model.addAttribute("roomTypeList", roomTypeList);
//            model.addAttribute("roomDTO", roomDTO);
//            model.addAttribute("id", id);
//            model.addAttribute("errImg", "Please choose at least one image!");
//            model.addAttribute("errImgTitle", "Please choose a image!");
//            model.addAttribute("room", roomService.findById(id));
//            return "/admin/room/edit";
//        }
        System.out.println(Objects.requireNonNull(roomDTO.getImageTitle().getOriginalFilename()).isEmpty());
        System.out.println(roomDTO.getImageTitle().getOriginalFilename());

        if(!roomDTO.getDeleteImage().isEmpty()) {
            System.out.println(roomDTO.getDeleteImage().size());
            System.out.println(roomDTO.getDeleteImage().get(0));
        }else{
            roomDTO.setDeleteImage(new ArrayList<>());
        }
//
        if (roomService.update(roomDTO,id)) {
            return "redirect:/admin/room";
        }
        return "redirect:/admin/room/edit" + id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        roomService.delete(id);
        return "redirect:/admin/room";
    }
}

    //    --------------------------------------------------------------------------
//
//    --------------------------------------------------------------------------


