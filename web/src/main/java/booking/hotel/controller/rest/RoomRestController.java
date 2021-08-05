package booking.hotel.controller.rest;


import booking.hotel.domain.GeneralInfoRoom;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.util.RoomSearchRequest;
import booking.hotel.domain.Room;
import booking.hotel.service.RoomProviderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/rooms")
@RequiredArgsConstructor
public class RoomRestController {

    private final RoomRepositoryData roomRepositoryData;
    private final RoomProviderService roomProviderService;

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

        return  roomProviderService.searchRoomByAllParams(request);

    }

    @ApiOperation(value = "deleted room by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idRoom", dataType = "string", paramType = "query", value = "ID for deleted Room"),

    })
    @DeleteMapping()
    public void delete(@RequestParam("idRoom") Long id ) {
        roomProviderService.deleteWithDependencies(id);
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

     return roomProviderService.saveOrUpdateWithAddedComforts(idRoom,roomInfo,comforts);



    }


    }


