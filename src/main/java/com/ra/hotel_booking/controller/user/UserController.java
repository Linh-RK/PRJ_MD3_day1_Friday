package com.ra.hotel_booking.controller.user;

import com.ra.hotel_booking.model.entity.*;
import com.ra.hotel_booking.model.entity.DTO.BookingDTO;
import com.ra.hotel_booking.model.entity.constants.AvailabilityStatus;
import com.ra.hotel_booking.model.entity.constants.BookingStatus;
import com.ra.hotel_booking.model.entity.constants.RoleName;
import com.ra.hotel_booking.model.entity.constants.RoomTypeName;
import com.ra.hotel_booking.model.service.admin.booking.BookingService;
import com.ra.hotel_booking.model.service.admin.customer.CustomerService;
import com.ra.hotel_booking.model.service.admin.room.RoomImagesService;
import com.ra.hotel_booking.model.service.admin.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes("customerLogin")
public class UserController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookingService  bookingService ;
    @Autowired
    private RoomImagesService roomImagesService;
    @Autowired
    private HttpSession session;


    //user HOME

    public static List<RoomTypeName> roomTypeList = Arrays.asList(RoomTypeName.values());
    public static List<AvailabilityStatus> availabilityStatusList = Arrays.asList(AvailabilityStatus.values());

    @GetMapping("/index")
    public String home(Model model,
                       HttpServletRequest request) {
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("roomTypes", roomTypeList);
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("searchBooking", new SearchBooking());
        return "/user/index";
    }
    // 1 user
    //   REGISTER ----------------------------------------------
    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "/user/login/signup";
    }
    @PostMapping("/signup")
    public String handleRegister(@ModelAttribute("customer") Customer customer, Model model) {
        customerService.register(customer);
        return "/user/login/login";
    }

    //  2 user  LOGIN----------------------------------------------
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("customer", new Customer());
        return "/user/login/login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("customer")Customer customer,Model model) {
        try{
            Customer customerLogin = customerService.login(customer);
            if(customerLogin != null) {
                if(customerLogin.getRoles().stream().anyMatch(c-> c.getRoleName().equals(RoleName.CUSTOMER))){
                    return "redirect:/user/index";
                }
            }
            model.addAttribute("admin", new Customer());
            return "/user/login/login";
        }catch (Exception e){
            model.addAttribute("customer", customer);
            model.addAttribute("error", "Email or Password is incorrect");
            return "/user/login/login";
        }
    }


    //  3 user-----------------------------------------------------
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return "/user/login/forgot-password";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,Model model) {
        //Xoa thong tin customer khoi session
        HttpSession session = request.getSession();
        session.invalidate(); //huy session
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("roomTypes", roomTypeList);
        model.addAttribute("rooms", roomService.findAll());
        return "/user/index";
    }


    //  4 user------------------------------------------------------
    @GetMapping("/about")
    public String about(Model model) {
        return "/user/about";
    }


    //  5 user-----------------------------------------------------
    @GetMapping("/blog-creative")
    public String blogCreative(Model model) {
        return "/user/blog-creative";
    }


    //  6 user-----------------------------------------------------
    @GetMapping("/blog-details")
    public String blogDetail(
            Model model) {
       return "/user/blog-details";
    }


    //  7 user-----------------------------------------------------
//    public int pageBooking;
//    public int totalPagesBooking;
    @GetMapping("/booking-system")
    public String bookingSystem(
            @ModelAttribute("searchBooking") SearchBooking searchBooking,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model,
            HttpServletRequest request, HttpSession httpSession) {
        List<Room> roomList;

        if (searchBooking.getAdults() == null) {
            searchBooking.setAdults(1);
        }
        if (searchBooking.getChildren() == null) {
            searchBooking.setChildren(0);
        }

        roomList = roomService.findAll(searchBooking);
        model.addAttribute("rooms", roomList);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        model.addAttribute("searchBooking", searchBooking);
        return "/user/booking-system";
    }

    private SearchBooking searchBooking;
    private Booking booking;
    @PostMapping("/booking-system")
    public String handleBookingSystem(
            @ModelAttribute("searchBooking") SearchBooking searchBooking,
            Model model) {
        List<Room> roomList;
        if (searchBooking.getAdults() == null) {
            searchBooking.setAdults(1);
        }
        if (searchBooking.getChildren() == null) {
            searchBooking.setChildren(0);
        }
        this.searchBooking = searchBooking;

        roomList = roomService.findAll(searchBooking);

        model.addAttribute("rooms", roomList);
        model.addAttribute("searchBooking", searchBooking);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        return "/user/booking-system";
    }


//    BOOKING STEP 2 -------------------------------------
    @PostMapping("/booking-step2")
    public String bookingStep2(
            @ModelAttribute("searchBooking") SearchBooking searchBooking,
//            @ModelAttribute("roomId") int roomId1,
            @RequestParam("roomId") int roomId, Model model,
            BindingResult result, RedirectAttributes redirectAttributes) {
        this.searchBooking = searchBooking;
        if(!roomService.isAvailble( roomId, searchBooking)){
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            model.addAttribute("searchBooking", this.searchBooking);
            redirectAttributes.addAttribute("roomId", roomId);
           redirectAttributes.addFlashAttribute("err","Phòng không khả dụng vào thời gian đã ");
            return "redirect:/user/rooms-details/{roomId}";
        }
        if( searchBooking.getStartDate().toString().equals(searchBooking.getEndDate().toString())){
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            model.addAttribute("searchBooking", this.searchBooking);
            redirectAttributes.addAttribute("roomId", roomId);
            redirectAttributes.addFlashAttribute("errDate", "Checkout date must greater than checkin date !");
            return "redirect:/user/rooms-details/{roomId}";
        }
        if (result.hasErrors()) {
            model.addAttribute("searchBooking", searchBooking);
            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            redirectAttributes.addAttribute("roomId", roomId);
            return "redirect:/user/rooms-details/{roomId}";
        } else {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setAdults(searchBooking.getAdults());
            bookingDTO.setChildren(searchBooking.getChildren());
            bookingDTO.setCheckIn(searchBooking.getStartDate());
            bookingDTO.setCheckOut(searchBooking.getEndDate());
            bookingDTO.setRoom(roomService.findById(roomId));
            bookingDTO.setCustomer((Customer) session.getAttribute("customerLogin"));
            System.out.println("Customer Login "+ bookingDTO.getCustomer().getCustomer_id());
            System.out.println("Customer Login "+ bookingDTO.getCustomer().getEmail());
            System.out.println("Customer Login "+ bookingDTO.getCustomer().getFirst_name());
            model.addAttribute("bookingDTO", bookingDTO);
            return "/user/booking-step2";
        }
    }


    //   BOOKING STEP 3  -------------------------------------
    @PostMapping("/booking-step3")
    public String handleBookingStep3(
            @ModelAttribute("bookingDTO") BookingDTO bookingDTO,
            Model model){
            Booking booking = new Booking(
                    bookingDTO.getCheckIn(),
                    bookingDTO.getCheckOut(),
                    (Customer) session.getAttribute("customerLogin"),
                    roomService.findById(bookingDTO.getRoom().getRoomId()),
                    bookingDTO.getAdults(),
                    bookingDTO.getChildren(),
                    bookingDTO.getTotalPrice());
        if (bookingService.create(booking)) {
            return "/user/booking-success";
        }
        return "/user/booking-step3";
    }


    //  8 user CONTACT -----------------------------------------------------
    @GetMapping("/contact")
    public String contact(Model model) {
        return "/user/contact";
    }


    //  9 user EVENT LIST-----------------------------------------------------
    @GetMapping("/events")
    public String events(Model model) {
        return "/user/events";
    }


    //  10 user EVENT DETAIL-----------------------------------------------------
    @GetMapping("/events-details")
    public String eventsDetails(Model model) {
        return "/user/events-details";
    }


    //  11 user FAQ -----------------------------------------------------
    @GetMapping("/faq")
    public String faq(Model model) {
        return "/user/faq";
    }


    //  12 user  GALLERY -----------------------------------------------------
    @GetMapping("/gallery-3column")
    public String gallery(Model model) {
        return "/user/gallery-3column";
    }


    //  13 user  PRICING TABLE-----------------------------------------------------
    @GetMapping("/pricing-tables")
    public String pricingTable(Model model) {
        return "/user/pricing-tables";
    }


    //  14 user  LIST ROOM-----------------------------------------------------
    public int page;
    public static String oldNameRoom = "";
    public int totalPages;

   @GetMapping("/rooms-col-2")
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
        if (search.getAdults() == null) {
            search.setAdults(1);
        }
        if (search.getChildren() == null) {
            search.setChildren(0);
        }
        int totalPages = roomService.totalPages(search);

        if (page + 1 > totalPages || page < 0) {
            page = totalPages - 1;
        }
        SearchBooking searchBooking = new SearchBooking();
        roomList = roomService.findAllPerPage(page, search);
        model.addAttribute("roomImages", roomImages);
        model.addAttribute("rooms", roomList);
        model.addAttribute("search", search);
        model.addAttribute("searchBooking", searchBooking);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("availabilityStatusList", availabilityStatusList);
        return "/user/rooms-col-2";

    }


    //  15 user ROOM DETAIL-----------------------------------------------------
    @GetMapping("/rooms-details/{id}")
    public String roomDetails(Model model,
                               @PathVariable("id") int roomId) {
        Room room = roomService.findById(roomId);
        List<String> amenities = room.getAmenities();
                model.addAttribute("room", room);
        model.addAttribute("id", roomId);
        model.addAttribute("amenities", amenities);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("searchBooking",searchBooking != null ? searchBooking : new SearchBooking());
        return "/user/rooms-details";
    }

    @PostMapping("/rooms-details/{id}")
    public String handleRoomDetails(@ModelAttribute("searchBooking") SearchBooking searchBooking
                                    , Model model,
                                    BindingResult result, RedirectAttributes redirectAttributes,
                                    @PathVariable int id) {
        if (result.hasErrors()) {

            model.addAttribute("roomTypeList", roomTypeList);
            model.addAttribute("availabilityStatusList", availabilityStatusList);
            return "redirect:/user/rooms-details/{roomId}";
        } else {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setAdults(searchBooking.getAdults());
            bookingDTO.setChildren(searchBooking.getChildren());
            bookingDTO.setCheckIn(searchBooking.getStartDate());
            bookingDTO.setCheckOut(searchBooking.getEndDate());
            bookingDTO.setRoom(roomService.findById(id));
            bookingDTO.setCustomer((Customer) session.getAttribute("customerLogin"));
            System.out.println("Customer Login "+ bookingDTO.getCustomer().getCustomer_id());
            System.out.println("Customer Login "+ bookingDTO.getCustomer().getEmail());
            System.out.println("Customer Login "+ bookingDTO.getCustomer().getFirst_name());
            model.addAttribute("bookingDTO", bookingDTO);
            return "redirect:/user/booking-step2";
        }
    }


    //  16 user STAFF-----------------------------------------------------
    @GetMapping("/staff")
    public String staff(Model model) {
        return "/user/staff";
    }


    //  17 user TYPOGRAPHY-----------------------------------------------------
    @GetMapping("/typography")
    public String typography(Model model) {
        return "/user/typography";
    }

    //18 BOOKING HISTORY-----------------------------------------------------

    private BookingStatus bookingStatus;
    public int pageBooking;
    public int totalPagesBooking;
    public static List<BookingStatus> bookingStatusList = Arrays.asList(BookingStatus.values());

    @GetMapping("/order-history")
    public String getBookings(@ModelAttribute("searchBookingDetail") SearchBookingDetail searchBookingDetail,
                              @RequestParam(name = "page", defaultValue = "0") int pageBooking,
                              @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                              @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                              Model model) {
        List<Booking> bookingList;
        if(searchBookingDetail.getSearchKey() == null){
            searchBookingDetail.setSearchKey("");
        }
        Customer customer = (Customer) session.getAttribute("customerLogin");
        totalPagesBooking = bookingService.totalPagesByCustomer(customer.getCustomer_id(),searchBookingDetail);

        if (pageBooking + 1 > totalPagesBooking || pageBooking < 0) {
            pageBooking = totalPagesBooking - 1;
        }

        bookingList = bookingService.findAllPerPageByCustomer(customer.getCustomer_id(),pageBooking,sortField,sortDir, searchBookingDetail);
        System.out.println(bookingList.size());
        System.out.println(bookingList.get(0).getBookingId());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("bookingStatusList", bookingStatusList);
        model.addAttribute("bookings", bookingList);
        model.addAttribute("searchBookingDetail", searchBookingDetail);
        model.addAttribute("page", pageBooking);
        model.addAttribute("totalPages", totalPagesBooking);
        return "/user/order-history";
    }

    @GetMapping("/order-history/delete/{id}")
    public String deleteBooking(
            @PathVariable("id") int bookingId, Model model) {
        bookingService.delete(bookingId);
        return "redirect:/user/order-history";
    }

    @GetMapping("/order-history/cancel/{id}")
    public String cancelBooking( @PathVariable("id") int id, Model model) {
        Booking booking = bookingService.findById(id);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingService.update(booking);
        return "redirect:/user/order-history";
    }

}
