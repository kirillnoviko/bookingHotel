package booking.hotel.controller.rest;


import booking.hotel.controller.request.RoomSearchRequest;
import booking.hotel.domain.Room;
import booking.hotel.domain.criteria.Criteria;
import booking.hotel.domain.criteria.SearchCriteria;
import booking.hotel.repository.RoomRepository;
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

        Criteria<SearchCriteria.Room> criteriaRoom= new Criteria<SearchCriteria.Room>();
        Criteria<SearchCriteria.DataInAndOut> criteriaData = new Criteria<DataInAndOut>();



        /*criteriaRoom.add(SearchCriteria.Room.PRICE,createRequest.getPrice());
        criteriaRoom.add(SearchCriteria.Room.PRINCIPLE_OF_PLACEMENT,createRequest.getPrincipleOfPlacement());
        criteriaRoom.add(SearchCriteria.Room.RATING_AVERAGE,createRequest.getRatingAverage());

        criteriaData.add(SearchCriteria.DataInAndOut.DATA_IN,createRequest.getDataIn());
        criteriaData.add(SearchCriteria.DataInAndOut.DATA_OUT,createRequest.getDataOut());

        return roomRepository.findCriteriaRoom(criteriaRoom,criteriaData,createRequest.getAdditionalComfort());*/
        return  roomRepository.findCriteriaRoom(request)   ;
    }


}
