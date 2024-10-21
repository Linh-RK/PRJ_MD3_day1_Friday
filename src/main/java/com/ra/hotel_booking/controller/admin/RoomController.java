package com.ra.hotel_booking.controller.admin;

import com.ra.hotel_booking.model.entity.DTO.RoomDTO;
import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
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
    public int page;
    public static String oldNameRoom = "";
    public int totalPages;
    public static List<RoomTypeName> roomTypeList = Arrays.asList(RoomTypeName.values());
    public static List<AvailabilityStatus> availabilityStatusList = Arrays.asList(AvailabilityStatus.values());

    @GetMapping
    public String index(@ModelAttribute("search") Search search,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        Model model) {
        List<Room> roomList ;

        if(search.getPriceMax() == null){
            search.setPriceMax(roomService.maxPrice());
        }
        totalPages = roomService.totalPages(search);

        if(page +1 > totalPages|| page < 0){
            page = totalPages - 1;
        }

        roomList = roomService.findAllPerPage(page, search);
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
    public String handleAdd(Model model,@Valid @ModelAttribute("roomDTO") RoomDTO roomDTO
    ,BindingResult result,RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || roomDTO.getImagedd().getSize() <= 0) {
            model.addAttribute("roomDTO", roomDTO);
//            redirectAttributes.addFlashAttribute("errImg", "Choose file !");
            model.addAttribute("errImg","Choose file !" );
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            return "/admin/room/add";
        }else {
            RoomDTO roomDTO1 = new RoomDTO(roomDTO.getRoomNumber(),roomDTO.getRoomType(),roomDTO.getAvailabilityStatus(),roomDTO.getDescription(),roomDTO.getImagedd());
            if (roomService.create(roomDTO1)) {
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
    public String edit(@PathVariable("id") Integer id,
                       Model model) {
        Room room = roomService.findById(id);
        RoomDTO roomDTO = new RoomDTO(room.getRoomNumber(), room.getRoomType(),room.getAvailabilityStatus(),room.getDescription());
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
        roomEntity.setHouseRules(roomDTO.getHouseRules());
        roomEntity.setAmenities(roomDTO.getAmenities());
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
