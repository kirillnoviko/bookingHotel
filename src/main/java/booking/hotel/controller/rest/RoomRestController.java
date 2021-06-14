package booking.hotel.controller.rest;


import booking.hotel.controller.request.RoomSearchRequest;
import booking.hotel.domain.Room;
import booking.hotel.domain.User;
import booking.hotel.domain.criteria.Criteria;
import booking.hotel.domain.criteria.SearchCriteria;
import booking.hotel.repository.RoomRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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
    public List<Room> createUser(@RequestBody RoomSearchRequest createRequest) {

        Criteria<SearchCriteria.Room> criteriaRoom= new Criteria<SearchCriteria.Room>();

        criteriaRoom.add(SearchCriteria.Room.PRICE,createRequest.getPrice());
        criteriaRoom.add(SearchCriteria.Room.PRINCIPLE_OF_PLACEMENT,createRequest.getPrincipleOfPlacement());
        criteriaRoom.add(SearchCriteria.Room.RATING_AVERAGE,createRequest.getRatingAverage());

        return roomRepository.findCriteriaRoom(criteriaRoom);
    }


}
