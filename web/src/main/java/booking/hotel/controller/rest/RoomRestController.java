package booking.hotel.controller.rest;


import booking.hotel.domain.RoomSearchRequest;
import booking.hotel.domain.Room;
import booking.hotel.domain.criteria.Criteria;
import booking.hotel.domain.criteria.SearchCriteria;
import booking.hotel.repository.RoomRepository;
import booking.hotel.service.RoomProviderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import booking.hotel.domain.criteria.SearchCriteria.*;
@RestController
@RequestMapping("/rest/rooms")
@RequiredArgsConstructor
public class RoomRestController {

    private final RoomRepository roomRepository;
    private final RoomProviderService roomProviderService;

    @GetMapping
    public List<Room> findAll() {
        return roomRepository.findAll();
    }


    @ApiOperation(value = "Search by criteria for the Room")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search was successfully!"),
            @ApiResponse(code = 500, message = "Internal server error ")
    })
  /*  @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })*/
    @PostMapping("/search")
    public List<Room> searchRoom( @ModelAttribute RoomSearchRequest request) {


        return  roomProviderService.searchByAllParamsRoom(request);
    }


}
