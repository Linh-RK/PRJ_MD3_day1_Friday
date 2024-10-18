package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.entity.DTO.RoomDTO;
import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomType;
import com.ra.hotel_booking.model.service.UploadFile.UploadFileService;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UploadFileService uploadFileService;
    public static List<RoomType> roomTypeList = Arrays.asList(RoomType.values());
    public static List<AvailabilityStatus> availabilityStatusList = Arrays.asList(AvailabilityStatus.values());
    @GetMapping
    public String index(Model model) {
        model.addAttribute("rooms", roomService.findAll());
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
    public String hadleAdd(Model model,@Valid @ModelAttribute("roomDTO") RoomDTO roomDTO
    ,BindingResult result,RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || roomDTO.getImagedd().getSize() <= 0) {
            model.addAttribute("roomDTO", roomDTO);
            redirectAttributes.addFlashAttribute("errImg", "Choose file !");
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            return "/admin/room/add";
        }else {
            if (roomService.create(roomDTO)) {
                return "redirect:/admin/room";
            }
            model.addAttribute("roomDTO", roomDTO);
            redirectAttributes.addFlashAttribute("errImg", "Choose file !");
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            return "redirect:/admin/room/add";
        }
    }
//    --------------------------------------------------------------------------
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Room room = roomService.findById(id);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomNumber(room.getRoomNumber());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setAvailabilityStatus(room.getAvailabilityStatus());
        roomDTO.setPricePerNight(room.getPricePerNight());
        roomDTO.setDescription(room.getDescription());
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("roomDTO", roomDTO );
        model.addAttribute("room", room );
        model.addAttribute("id", id);
        return "/admin/room/edit";
    }

    @PostMapping("/edit/{id}")
    public String update( @Valid @ModelAttribute("roomDTO") RoomDTO roomDTO,
                          BindingResult result,
                          @PathVariable int id,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            List<Room> categories = roomService.findAll();
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("roomDTO", roomDTO );
            model.addAttribute("id", id);
            model.addAttribute("room", roomService.findById(id));
            redirectAttributes.addFlashAttribute("errImg", "Choose file !");
            return "/admin/room/edit"+ id;
        }
        Room roomEntity = roomService.findById(id);
        roomEntity.setRoomNumber(roomDTO.getRoomNumber());
        roomEntity.setRoomType(roomDTO.getRoomType());
        roomEntity.setAvailabilityStatus(roomDTO.getAvailabilityStatus());
        roomEntity.setPricePerNight(roomDTO.getPricePerNight());
        roomEntity.setDescription(roomDTO.getDescription());
        if (roomDTO.getImagedd().getSize()>0) {
            String bookImage = uploadFileService.uploadFile (roomDTO.getImagedd());
            roomEntity.setImage(bookImage);
        }
        if(roomService.update(roomEntity)){
            return "redirect:/admin/room";
        }

        return "redirect:/admin/room/edit" + id;
    }
//    --------------------------------------------------------------------------
    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        roomService.delete(id);
        return "redirect:/admin/room";
    }
//    --------------------------------------------------------------------------

}
