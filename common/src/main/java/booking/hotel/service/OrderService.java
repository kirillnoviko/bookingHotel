package booking.hotel.service;


import booking.hotel.domain.Order;
import booking.hotel.domain.StatusName;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepositoryData orderRepositoryData;


}
