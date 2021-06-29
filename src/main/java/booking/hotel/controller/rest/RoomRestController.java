package booking.hotel.controller.rest;


import booking.hotel.controller.request.RoomSearchRequest;
import booking.hotel.domain.Room;
import booking.hotel.domain.criteria.Criteria;
import booking.hotel.domain.criteria.SearchCriteria;
import booking.hotel.repository.RoomRepository;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value = "Search criteria")
    @PostMapping("/search")
    public List<Room> searchRoom(@RequestBody RoomSearchRequest createRequest) {

        Criteria<SearchCriteria.Room> criteriaRoom= new Criteria<SearchCriteria.Room>();
        Criteria<SearchCriteria.DataInAndOut> criteriaData = new Criteria<DataInAndOut>();

        criteriaRoom.add(SearchCriteria.Room.PRICE,createRequest.getPrice());
        criteriaRoom.add(SearchCriteria.Room.PRINCIPLE_OF_PLACEMENT,createRequest.getPrincipleOfPlacement());
        criteriaRoom.add(SearchCriteria.Room.RATING_AVERAGE,createRequest.getRatingAverage());

        criteriaData.add(SearchCriteria.DataInAndOut.DATA_IN,createRequest.getDataIn());
        criteriaData.add(SearchCriteria.DataInAndOut.DATA_OUT,createRequest.getDataOut());
        return roomRepository.findCriteriaRoom(criteriaRoom,criteriaData);
    }


}
