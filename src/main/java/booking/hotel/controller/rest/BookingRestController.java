package booking.hotel.controller.rest;


import booking.hotel.controller.request.RoomSearchRequest;
import booking.hotel.domain.Booking;
import booking.hotel.domain.Room;
import booking.hotel.domain.criteria.Criteria;
import booking.hotel.domain.criteria.SearchCriteria;
import booking.hotel.repository.BookingRepository;
import booking.hotel.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/booking")
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingRepository bookingRepository;

    @ApiOperation(value = "Search orders user")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/search")
    public List<Booking> createUser(@ApiIgnore Principal principal) {
        return bookingRepository.findAllOrdersUser(principal.getName());
    }

    @GetMapping()
    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
}
