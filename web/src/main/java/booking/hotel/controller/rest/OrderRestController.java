package booking.hotel.controller.rest;


import booking.hotel.controller.exception.ErrorMessage;
import booking.hotel.domain.Order;
import booking.hotel.domain.StatusName;
import booking.hotel.repository.OrderRepository;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.request.OrderChangeRequest;
import booking.hotel.request.OrderCreateRequest;
import booking.hotel.request.OrderSearchRequest;
import booking.hotel.service.OrderService;
import booking.hotel.service.RoomService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/orders")
@RequiredArgsConstructor
public class OrderRestController  {

    private final OrderRepositoryData orderRepository;
    private final UserRepositoryData userRepository;
    private final OrderService orderService;
    public final ConversionService conversionService;

    @ApiOperation(value = "show all orders")
    @GetMapping("/admin/")
    public List<Order> findAll(){
        return orderRepository.findAll();
    }


    @ApiOperation(value = "show all orders of auth User")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping()
    public List<Order> findAllOrdersUser(@ApiIgnore Principal principal){
        return orderRepository.findByUserGmail(principal.getName());
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{status}")
    public List<Order> findOrdersUserByStatus(@ApiIgnore Principal principal, @PathVariable StatusName status){
        return orderRepository.findByUserGmailAndStatus(principal.getName(),status);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/search")
    public List<Order> findOrdersUserByAllParams(@ApiIgnore Principal principal, @RequestBody OrderSearchRequest request){
        Order orderSearch=conversionService.convert(request, Order.class);

        if(!userRepository.findByGmail(principal.getName()).isEmpty()){
            orderSearch.setUser(userRepository.findByGmail(principal.getName()).get());
        }else
        {
            throw new RuntimeException("user with this login does not exist");
        }

        return orderService.findAllParams(orderSearch);
    }



    @ApiOperation(value = "save order for auth user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping()
    public Order save(@ApiIgnore Principal principal, @RequestBody OrderCreateRequest request){
       Order order=conversionService.convert(request, Order.class);


       if(!userRepository.findByGmail(principal.getName()).isEmpty()){
           order.setUser(userRepository.findByGmail(principal.getName()).get());
       }else
       {
            throw new RuntimeException("user with this login does not exist");
       }

        return orderService.createOrder(order);
    }


    @ApiOperation(value = "update order for auth user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping ()
    public Order update(@ApiIgnore Principal principal, @RequestBody OrderChangeRequest request){

        Order order=conversionService.convert(request, Order.class);

        return orderService.createOrder(order);
    }

    @ApiOperation(value = "update statusName Order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idOrder", dataType = "string", paramType = "query", value = "idOrder for Order"),
            @ApiImplicitParam(name = "statusName", dataType = "string", paramType = "query", value = "statusName for update Order "),
    })
    @PutMapping ("/admin")
    public Order updateStatusName(@RequestParam Long idOrder,@RequestParam StatusName statusName){

        return orderService.updateStatusName(idOrder,statusName);
    }

}
