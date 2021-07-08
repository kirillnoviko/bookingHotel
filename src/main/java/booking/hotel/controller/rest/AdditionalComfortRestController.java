package booking.hotel.controller.rest;

import booking.hotel.domain.AdditionalComfort;
import booking.hotel.domain.Role;
import booking.hotel.repository.AdditionalComfortRepository;
import booking.hotel.repository.RoleRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest/additional")
@RequiredArgsConstructor
public class AdditionalComfortRestController {
    private final AdditionalComfortRepository additionalComfortRepository;



    @ApiImplicitParams({
            @ApiImplicitParam(name = "idAdditionalComfort", dataType = "string", paramType = "query", value = "id additional comfort for output room"),

    })
    @GetMapping
    public AdditionalComfort findAll(@RequestParam Long idAdditionalComfort) {
        System.out.println("In rest controller");
        return additionalComfortRepository.findOne(idAdditionalComfort);
    }
}