package booking.hotel.controller.rest;


import booking.hotel.domain.Order;
import booking.hotel.repository.OrderRepository;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderRepositoryData orderRepository;

    @ApiOperation(value = "Search orders user")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/search")
    public List<Order> createUser(@ApiIgnore Principal principal) {
        return null;
        //return orderRepository.findAllOrdersUser(principal.getName());
    }

    @GetMapping()
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
}
