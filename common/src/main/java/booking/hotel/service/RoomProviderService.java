package booking.hotel.service;

import booking.hotel.domain.Comfort;
import booking.hotel.domain.Room;
import booking.hotel.exception.NoParamsException;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.ComfortRepository;
import booking.hotel.repository.dataspring.ComfortRepositoryData;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.util.RoomSearchRequest;
import booking.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomProviderService {
    private final RoomRepositoryData roomRepositoryData;

    public List<Room> searchRoomByAllParams(RoomSearchRequest request) throws RuntimeException{

        List<Room> resultRooms = new ArrayList<>();
        List<Room> roomWithParams = new ArrayList<>();
        List<Room> roomAdditionalComforts = new ArrayList<>();


        Long minPrice=request.getPriceMin();
        Long maxPrice=request.getPriceMax();
        Long minRating=request.getRatingMin();
        Long maxRating=request.getRatingMax();
        String principleOfPlacement=request.getPrincipleOfPlacement();


        if(!request.getIdComfort().isEmpty()) {

            roomAdditionalComforts = roomRepositoryData.findByListComfortsRoom(request.getIdComfort());
            //TODO exception for no suchID, list null
        }
        else {
            roomAdditionalComforts = roomRepositoryData.findByDeletedIsFalse();
        }

        if(minPrice!=null || maxPrice!=null || minRating!=null
                || maxRating!=null  || principleOfPlacement!=null){

            roomWithParams = roomRepositoryData.findByParamsRoom(minPrice,maxPrice,minRating,maxRating,principleOfPlacement);
            //TODO exception list null
        }
        else {
            roomWithParams = roomRepositoryData.findByDeletedIsFalse();
        }


        if(!roomAdditionalComforts.equals(roomWithParams)){
            for(Room room : roomAdditionalComforts){
                for(Room room1: roomWithParams)
                {
                    if(room.getId()==room1.getId()){
                        resultRooms.add(room);
                        break;
                    }
                }
            }
        }else{
            return searchRoomByData(roomWithParams,request.getDataIn(),request.getDataOut());
        }

        resultRooms=searchRoomByData(resultRooms,request.getDataIn(),request.getDataOut());
        //TODO exception

        return resultRooms;

    }
    public List<Room> searchRoomByData(List<Room> rooms, Timestamp dataIn, Timestamp dataOut){
        List<Room> bookingRooms = new ArrayList<>();


        if(dataIn==null || dataOut == null){
            //TODO no such data
        }else if(dataIn==null && dataOut ==null ){
                return rooms;
        }else{
                bookingRooms = roomRepositoryData.findByData(rooms,dataIn,dataOut);
        }


        for(Room bookingRoom : bookingRooms){
            for(Room room : rooms){
                if(room.getId()==bookingRoom.getId()){
                    rooms.remove(room);
                    break;
                }
            }
        }


        return rooms;
    }

}
