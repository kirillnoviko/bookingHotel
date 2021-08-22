package booking.hotel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import booking.hotel.domain.GeneralInfoRoom;
import booking.hotel.domain.Room;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.util.EntityForSearchRoom;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepositoryData roomRepositoryData;

    public List<Room> searchRoomByAllParams(EntityForSearchRoom entity) throws RuntimeException{

        return roomRepositoryData.findByListComfortsRoom(entity);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteWithDependencies(Long id){
        roomRepositoryData.deleteDependenciesComforts(id);
        roomRepositoryData.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public Room saveOrUpdateWithAddedComforts(Long id, GeneralInfoRoom generalInfoRoom, List<Long> comforts){

        Room room = new Room();

        if(id!=null){
            room=roomRepositoryData.findById(id).get();

            if(generalInfoRoom.getName()!=null){
                room.getGeneralInfoRoom().setName(generalInfoRoom.getName());
            }
            if(generalInfoRoom.getPrice()!=null){
                room.getGeneralInfoRoom().setPrice(generalInfoRoom.getPrice());
            }
            if(generalInfoRoom.getNumberRoom()!=null){
                room.getGeneralInfoRoom().setNumberRoom(generalInfoRoom.getNumberRoom());
            }
            if(generalInfoRoom.getPrincipleOfPlacement()!=null){
                room.getGeneralInfoRoom().setPrincipleOfPlacement(generalInfoRoom.getPrincipleOfPlacement());
            }
            if(generalInfoRoom.getComfortLevel()!=null){
                room.getGeneralInfoRoom().setComfortLevel(generalInfoRoom.getComfortLevel());
            }

        }else{
            room.setGeneralInfoRoom(generalInfoRoom);
            room.setRatingAverage(5l);
        }

        room = roomRepositoryData.save(room);

        if (comforts != null) {
            for(Long comfort : comforts){
                roomRepositoryData.createSomeRow(room.getId(),comfort);
            }
        }

        return  room;
    }
}
