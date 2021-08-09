package booking.hotel.controller.rest;

import booking.hotel.domain.Comfort;
import booking.hotel.repository.dataspring.ComfortRepositoryData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/rest/comforts")
@RequiredArgsConstructor
public class ComfortRestController {
    private final ComfortRepositoryData comfortRepository;



    @ApiImplicitParams({
            @ApiImplicitParam(name = "idAdditionalComfort", dataType = "string", paramType = "query", value = "id additional comfort for output room"),

    })
    @GetMapping
    public List<Comfort> findAll(@RequestParam Long idAdditionalComfort) {
        System.out.println("In rest controller");
        return comfortRepository.findAll();
    }
}