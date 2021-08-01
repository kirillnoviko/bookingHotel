package booking.hotel.service;

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
    private final ComfortRepositoryData  comfortRepositoryData;
    private  final OrderRepositoryData orderRepositoryData;


    public List<Room> searchRoomByAllParams(RoomSearchRequest request) throws RuntimeException{

        List<Room> resultRooms = new ArrayList<>();
        List<Room> roomWithParams = new ArrayList<>();
        List<Room> roomAdditionalComforts = new ArrayList<>();

            if(!request.getIdComfort().isEmpty()){
                for(Long id : request.getIdComfort())
                {
                    try {
                        comfortRepositoryData.findById(id).get();
                    }
                    catch (RuntimeException E){
                        throw new NoParamsException("no Such ID:" + id);
                    }
                }
                roomAdditionalComforts = roomRepositoryData.findByListComfortsRoom(request.getIdComfort());
            }


        try {
            roomWithParams = roomRepositoryData.findByParamsRoom(request);
        }
        catch (NoParamsException E){
            resultRooms=searchRoomByData(roomAdditionalComforts,request.getDataIn(),request.getDataOut());
        }

        if(roomAdditionalComforts.isEmpty() || roomWithParams.isEmpty()){
            throw  new NoSuchEntityException("rooms with such parameters were not found");
        }


        for(Room room : roomAdditionalComforts){
           for(Room room1: roomWithParams)
           {
               if(room.getId()==room1.getId()){
                    resultRooms.add(room);
                    break;
               }
           }
        }

        resultRooms=searchRoomByData(resultRooms,request.getDataIn(),request.getDataOut());


        return resultRooms;
    }
    public List<Room> searchRoomByData(List<Room> rooms, Timestamp dataIn, Timestamp dataOut){


        return null;
    }

}
