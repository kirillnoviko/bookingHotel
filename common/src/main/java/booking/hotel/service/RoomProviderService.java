package booking.hotel.service;

import booking.hotel.domain.Room;
import booking.hotel.domain.RoomSearchRequest;
import booking.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomProviderService {
    private final RoomRepository roomRepository;
    public List<Room> searchByAllParamsRoom(RoomSearchRequest request){

        List<Room> result =roomRepository.findByListComfortsRoom(request.getAdditionalComfort());

        return null;
    }

}
