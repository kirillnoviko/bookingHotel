package booking.hotel.controller.rest;


import booking.hotel.domain.GeneralInfoRoom;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.util.EntityForSearchRoom;
import booking.hotel.request.RoomSearchRequest;
import booking.hotel.domain.Room;
import booking.hotel.service.RoomService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/rooms")
@RequiredArgsConstructor
public class RoomRestController {

    private final RoomRepositoryData roomRepositoryData;
    private final RoomService roomService;
    public final ConversionService conversionService;

    @ApiOperation(value = "show all rooms")
    @GetMapping()
    public List<Room> findAll() {
        System.out.println("in rest controller");
        return roomRepositoryData.findByDeletedIsFalse();
    }

    @ApiOperation(value = "find room by id")
    @GetMapping("/{idRoom}")
    public Room findOne(@PathVariable("idRoom") Long id ){
        return roomRepositoryData.findByIdAndDeletedIsFalse(id).get();
    }


    @ApiOperation(value = "find Room by all parameters ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search was successfully!"),
            @ApiResponse(code = 500, message = "Internal server error ")
    })
    @PostMapping("/search")
    public List<Room> searchRoom(@RequestBody RoomSearchRequest request) {
           EntityForSearchRoom entity = conversionService.convert(request, EntityForSearchRoom.class);
            return  roomService.searchRoomByAllParams(entity);
    }

    @ApiOperation(value = "deleted room by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idRoom", dataType = "string", paramType = "query", value = "ID for deleted Room"),

    })
    @DeleteMapping()
    public void delete(@RequestParam("idRoom") Long id ) {
        roomService.deleteWithDependencies(id);
    }


    @ApiOperation(value = "created or update Room  ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully!"),
            @ApiResponse(code = 500, message = "Internal server error ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idRoom", dataType = "string", paramType = "query", value = "IdRoom for update")
    })
    @PostMapping()
    public Room saveRoom(@ModelAttribute GeneralInfoRoom roomInfo, @RequestParam Long idRoom, @RequestBody List<Long> comforts) {

     return roomService.saveOrUpdateWithAddedComforts(idRoom,roomInfo,comforts);

    }


    }


